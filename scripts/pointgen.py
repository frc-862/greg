import pandas as pd
import math
import numpy 

file_to_read = input("type the file you want to read here: ")

read = pd.read_csv(file_to_read)
angle_calced = numpy.arctan2(read["Tangent Y"], read["Tangent X"])

combined_output = []
combined_output2 = []
for index in range(len(read["X"])):

    x_gen = ((read["X"][index]) - (read["X"][0]))
    y_gen = ((read["Y"][index]) - (read["Y"][0]))
    angle_gen = numpy.rad2deg(angle_calced[index])

    x_val = numpy.round(x_gen, 3)
    y_val = numpy.round(y_gen, 3)
    angle = numpy.round(angle_gen, 3)

    combined_output.append((x_val, y_val, angle)) 
    combined_output2.append((read["X"][index], read["Y"][index], angle))

    print("new Pose2d(" + str(x_val) + "d, " + str(y_val) + "d, " + "Rotation2d.fromDegrees(" + str(angle) + "d" ")),")

print("points relative to 0: ")
print(numpy.array(combined_output))
print("points not relative to 0: ")
print(numpy.array(combined_output2))
