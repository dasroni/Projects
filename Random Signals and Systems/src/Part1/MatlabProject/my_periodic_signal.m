function x = my_periodic_signal(t,A,B,f_1,f_2,phi_1,phi_2)
%% Function parameters
% t - time
% A - amplitude of sine wave
% B - amplitude of cosine wave
% f_1 - frequency of sine wave
% f_2 - frequency of cosine wave
% phi_1 - phase of sine wave
% phi_2 - phase of cosine wave

%% Actual function
x=A.*sin(2.*pi.*f_1.*t+phi_1)+B.*cos(2.*pi.*f_2.*t+phi_2);


end

