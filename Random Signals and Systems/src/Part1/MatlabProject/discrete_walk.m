function [x_position, t_step] = discrete_walk(height, width, x_initial, x_final)

x_position = x_initial;
t_step = 1;


while(x_position(t_step,:) ~= x_final)
    
    step1 = unidrnd(3, 1, 2) - 2;   % NOT unidrnd(3, 2, 1)
    x_tmp = x_position(t_step,:) + step1;
    
    if( x_tmp(1) >= 0 && x_tmp(1) <= width && x_tmp(2) >= 0 && x_tmp(2) <= height )
        %x_tmp (1) denotes column 1 and x_tmp(2) denotes column 2
        t_step = t_step + 1;
        x_position(t_step, :) = x_tmp;
        %the next position is equal to the current position + the randomly
        %generated integer
    end
end
end

