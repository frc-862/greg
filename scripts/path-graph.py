from tkinter import *
from matplotlib.figure import Figure
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg, NavigationToolbar2Tk
from tkinter import filedialog
import math

def pickFile():
    pathh.delete(0, END)
    pathh.insert(END, 'Reading File . . .') 
    pathh.update()
    tf = filedialog.askopenfilename(initialdir='.', title='Open Waypoint File', filetypes=(('Waypoint Files', '*.waypoints'),))
    pathh.delete(0, END)
    pathh.insert(END, tf)
    pathh.update()

def plot():
    x, y, theta = [], [], []
    if len(pathh.get()) == 0:
        return
    with open(pathh.get()) as pointsFile:
        for line in pointsFile.readlines()[1:]:
            a = line.split()
            x.append(float(a[0]))
            y.append(float(a[1]))
            theta.append(float(a[2]))
    figure = Figure(figsize=(5,5), dpi=100)
    path = figure.add_subplot(111)

    path.plot(x, y, color='#fe9900', lw=1.0)
    for x_, y_, t_ in zip(x, y, theta):
        t = math.radians(t_ + 180)
        path.arrow(x_, y_, -math.cos(t), -math.sin(t), shape='full', lw=1, length_includes_head=True, head_width=0.1, color='#0000ff')

    canvas = FigureCanvasTkAgg(figure, master=window)   
    canvas.draw()
    canvas.get_tk_widget().pack() 
    toolbar = NavigationToolbar2Tk(canvas, window) 
    toolbar.update()
    canvas.get_tk_widget().pack()

window = Tk()
window.title('Path Graph')
window.geometry('750x750')

Button(master=window, command=plot, height=2, width=10, text='Plot').pack()
pathh = Entry(window)
pathh.pack()
Button(window, text='Open File', command=pickFile).pack()

window.mainloop()
