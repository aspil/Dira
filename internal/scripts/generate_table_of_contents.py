#! /usr/bin/python3
import argparse
import sys
import re


class Header:
    def __init__(self, header_level, text):
        self.header_level = header_level
        self.text = text

    def to_table_of_contents_intra_link(self):
        # remove any punctuation
        link = re.sub(r'[^\w\s]', '', self.text)
        link = link.lower().replace(' ', '-')

        intra_link = f"[{self.text}](#{link})"

        if self.header_level <= 2:
            intra_link = f"**{intra_link}**"

        return intra_link


def get_header_number(s):
    i = 0
    while s[i] == '#':
        i += 1
    return i


def parse_file_contents(file_contents):
    table_of_contents = []
    for line in file_contents.splitlines():
        if line.startswith('#'):
            header_number = get_header_number(line)
            if header_number >= 2:
                text = str(line[header_number:]).lstrip()
                table_of_contents.append(Header(header_number, text))

    return table_of_contents


def format_table_of_contents(toc):
    rows = []
    rows.append("# Table of Contents")
    for (i, row) in enumerate(toc):
        intra_link = row.to_table_of_contents_intra_link()
        if row.header_level == 2:
            # Hacker :^)
            if i != 0:
                rows[i] = rows[i][:len(rows[i]) - 1]
            intra_link = f"\n{intra_link}"
        rows.append(f"{intra_link}\\")

    # Hacker :^)
    rows[i + 1] = rows[i + 1][:len(rows[i + 1]) - 1]
    rows.append('\n')
    return '\n'.join(rows)


def process_files(cli):
    for f in cli.file:
        file_contents = f.read()
        table_of_contents = parse_file_contents(file_contents)
        if table_of_contents:
            table_of_contents = format_table_of_contents(table_of_contents)
            if cli.update_in_place:
                f.close()
                with open(f.name, "w") as f:
                    f.write(table_of_contents)
                    f.write(file_contents)
            else:
                print(f"----- {f.name} -----")
                print(table_of_contents)


def parse_arguments():
    parser_arguments = {
        ("--update-in-place",): {
            "action": "store_true",
            "default": False,
            "help": "Update the files in place with the table of contents",
        },
        ("file",): {
            "type": argparse.FileType('r'),
            "nargs": "+",
        }
    }

    parser = argparse.ArgumentParser()
    for (arg, arg_info) in parser_arguments.items():
        parser.add_argument(*arg, **arg_info)

    return parser.parse_args()


def main():
    cli = parse_arguments()
    process_files(cli)


if __name__ == "__main__":
    main()
