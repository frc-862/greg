import pandas as pd
import math
import numpy 

file_to_read = input("Path File: ")
file_to_read_ = input("First Path File: ")

read = pd.read_csv(file_to_read)
read_ = pd.read_csv(file_to_read_)
angle_calced = numpy.arctan2(read["Tangent Y"], read["Tangent X"])

combined_output = []
for index in range(len(read["X"])):
    x_gen = ((read["X"][index]) - (read_["X"][0]))
    y_gen = ((read["Y"][index]) - (read_["Y"][0]))
    angle_gen = numpy.rad2deg(angle_calced[index])

    x_val = numpy.round(x_gen, 3)
    y_val = numpy.round(y_gen, 3)
    angle = numpy.round(angle_gen, 3)

    combined_output.append((x_val, y_val, angle))

    print("new Pose2d(" + str(x_val) + "d, " + str(y_val) + "d, " + "Rotation2d.fromDegrees(" + str(angle) + "d" ")),")

numpy.set_printoptions(suppress=True, precision=3)
# print("points relative to 0: ")
# print(numpy.array(combined_output))
