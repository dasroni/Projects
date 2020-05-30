clear all
clc
close all


T = 2000;
x(1) = 2;
xx(1) = 2;
xxx(1) = 2;
count1 = 0;
count2 = 0;
count3 = 0;

for t=2:T
    current1 = x(t-1);
    proposal1 = current1 + normrnd(0, sqrt(.1));
    a1 = target_1(proposal1) / target_1(current1);
    u1 = unifrnd(0,1);
    if(u1 < a1)
        x(t) = proposal1;
        count1 = count1 + 1; % tracking the # accepted proposals
    else
        x(t) = current1;
    end
end
acc_prob1 = count1/T; % acceptance probability
meanx = mean(x); % mean of x
subplot(3,2,1);
plot(x);
subplot(3,2,2);
histogram(x);

for t=2:T
    current2 = xx(t-1);
    proposal2 = current2 + normrnd(0, sqrt(.1));
    a2 = target_2(proposal2) / target_2(current2);
    u2 = unifrnd(0,1);
    if(u2 < a2)
        xx(t) = proposal2;
        count2 = count2 + 1; % tracking the # accepted proposals
    else
        xx(t) = current2;
    end
end

acc_prob2 = count2/T; % acceptance probability
meanxx = mean(xx); % mean of x
subplot(3,2,3);
plot(xx);
subplot(3,2,4);
histogram(xx);

for t=2:T
    current3 = xxx(t-1);
    proposal3 = current3 + normrnd(0, sqrt(.1));
    a3 = target_3(proposal3) / target_3(current3);
    u3 = unifrnd(0,1);
    if(u3 < a3)
        xxx(t) = proposal3;
        count3 = count3 + 1; % tracking the # accepted proposals
    else
        xxx(t) = current3;
    end
end

acc_prob3 = count3/T; % acceptance probability
meanxxx = mean(xxx); % mean of x
subplot(3,2,5);
plot(xxx);
subplot(3,2,6);
histogram(xxx);

