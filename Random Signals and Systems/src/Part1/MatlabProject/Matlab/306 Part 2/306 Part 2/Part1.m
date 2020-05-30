clear all
clc
close all


M = 100000;

for m = 1: M
    [x_initial, t_step(m)] = discrete_walk (5, 5, [0 0], [3 3]) ;
end
histogram(t_step, 30)

mean(t_step)
var(t_step)


