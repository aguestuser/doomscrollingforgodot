"""
Script for parsing structured JSON data objects w/ `speaker` & `line` k/v pairs
from raw fulltext of *Waiting For Godot*.

Usage:

Assuming this script lives in a directory containing a file called `fulltext.txt`,
you can generated `fulltext.json` in that same directory by running:

```shell
python parse.py
```

"""

from dataclasses import dataclass
from dataclasses_json import dataclass_json
from enum import Enum
from typing import List

#############################
# CONSTANTS & DOMAIN OBJECTS
#############################


RAW_TEXT_PATH = "fulltext.txt"
JSON_PATH = "fulltext.json"
SPEAKER_NAMES = {
    "VLADIMIR",
    "ESTRAGON",
    "VLADIMIR and ESTRAGON",
    "LUCKY",
    "POZZO",
    "BOY",
}

@dataclass_json
@dataclass
class SpokenLine:
    speaker: str
    line: str

@dataclass
class Tokenized:
    remaining_input: str
    token: str


############
# FUNCTIONS
############

def clean(input: str) -> str:
    for speaker_name in SPEAKER_NAMES:
        input = input.replace(f"{speaker_name}:", f" {speaker_name}: ")
    return input

def take_token(input: str) -> (str, str):
    """
    Consumes first token from string input
    Returns tuple of (token, remaining string)
    """
    token = ""
    for (i, char) in enumerate(input):
        if char.isspace() and token:
            return (token, input[i+1:])
        if char.isspace() and not token:
            continue # skip leading whitespace
        token += char
    return (token, "") # if we have reached end of input

# take_tokens_while_not_speaker("foo bar blah blam ESTRAGON:\n lala")
def take_tokens_while_not_speaker(input: str) -> (List[str], str):
    """
    Consumes input string until it finds a speaker token
    Returns tuple of (list of consumed tokens, remaining string)
    """
    tokens = []
    while input:
        (token, remaining_input) = take_token(input)
        if token.replace(":", "") in SPEAKER_NAMES:
            return (tokens, input)
        else:
            tokens.append(token)
            input = remaining_input
    return(tokens,input)

def take_speaker_name(input: str) -> (str, str):
    """
    Consumes the input until it has taken a full `SpeakerName`,
    which will normally be only one token, but may be 3 tokens
    in the special case of `"VLADIMIR and ESTRAGON"`.
    Returns a tuple of the `SpeakerName` and remaining input.
    """
    speaker_name, remaining_input = take_token(input)
    speaker_name = speaker_name.replace(":", "")
    if speaker_name not in SPEAKER_NAMES:
        raise Exception(f"{speaker_name} not in {SPEAKER_NAMES}")
    if speaker_name == "VLADIMIR" and remaining_input.startswith("and"):
        print("found one!!!")
        speaker_name = "VLADIMIR and ESTRAGON"
        remaining_input = remaining_input[len(" and ESTRAGON"):]
    return speaker_name, remaining_input

# parse("foo bar ESTRAGON:\n nothing to be done. \nVLADIMIR: i'm beginning to come around to that opinion.")
def parse(input: str) -> List[SpokenLine]:
    """
    Parses `SpokenLine`s from a string input; trims leading/trailing whitespace.
    Returns list of `SpokenLine` data objects
    """
    spoken_lines = []
    (_, input) = take_tokens_while_not_speaker(input)
    while input:
        speaker, input  = take_speaker_name(input)
        line_tokens, input  = take_tokens_while_not_speaker(input)
        spoken_lines.append(SpokenLine(speaker.replace(":", ""), " ".join(line_tokens)))
    return spoken_lines


############
# EXECUTION
############


spoken_lines = []
with open(RAW_TEXT_PATH, "r") as f:
     spoken_lines = parse(clean(f.read()))

spoken_lines_json = SpokenLine.schema().dumps(
    spoken_lines, indent=4, many=True, sort_keys=True
)

with open(JSON_PATH, "w") as f:
    f.write(spoken_lines_json)

print(f"parsed {len(spoken_lines)} spoken lines!")
