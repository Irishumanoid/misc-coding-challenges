import GCodeParser
import numpy as np
cimport numpy as np
cimport cython

@cython.boundscheck(False)
@cython.wraparound(False)
cdef public int* getArray(str file, str param):
    cmds = GCodeParser.get_Gcode_attr(GCodeParser.parse_Gcode(file));
    cdef np.ndarray[np.float64_t, ndim=2, mode='c'] result = cmds
    return result
    