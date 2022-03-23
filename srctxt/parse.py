from dataclasses import dataclass
from dataclasses_json import dataclass_json
from enum import Enum

RAW_TEXT_PATH = "fulltext.txt"
JSON_PATH = "fulltext.json"
Speaker = Enum("Character", "VLADIMIR ESTRAGON LUCKY POZZO BOY")
SPEAKER_NAMES = {s.name for s in Speaker}


@dataclass_json
@dataclass
class SpokenLine:
    speaker: str
    line: str


def trim(character_name: str) -> str:
    return character_name.replace(":", "").replace(" ", "")


with open(RAW_TEXT_PATH, "r") as f:
    lines = [line.replace("\n", " ") for line in f.readlines()]

speaker_name_idxs = [
    idx for idx, line in enumerate(lines) if trim(line) in SPEAKER_NAMES
]

spoken_lines = []
for idxs_idx, speaker_idx in enumerate(speaker_name_idxs):
    speaker = trim(lines[speaker_idx])
    line_start = speaker_idx + 1
    line_end = (
        speaker_name_idxs[idxs_idx + 1]
        if idxs_idx + 1 < len(speaker_name_idxs) - 1
        else len(lines)
    )
    line = "".join(lines[line_start:line_end])
    spoken_lines.append(SpokenLine(speaker, line))

spoken_lines_json = SpokenLine.schema().dumps(
    spoken_lines, indent=4, many=True, sort_keys=True
)

with open(JSON_PATH, "w") as f:
    f.write(spoken_lines_json)

print(f"parsed {len(spoken_lines)} spoken lines from {len(lines)} textual lines!")
