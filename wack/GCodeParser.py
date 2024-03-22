from gcodeparser import GcodeParser, GcodeLine

#requirements to run: latest version of python, IDE with python dependency, gcodeparser extension (pip install)
cmds = []

def parse_Gcode(file):
    with open(file, 'r') as f:
        gcode = f.read()

    return GcodeParser(gcode, include_comments=True).lines

def get_Gcode_attr(gcode:list[GcodeLine], param:str) -> list[str]:
    for command in gcode:
        cmds.append(command.get_param(param))
    return cmds
