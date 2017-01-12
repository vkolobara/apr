files = glob('.\*.out');
figure();
hold on;
names = {};
for i=1:numel(files)
  x = load(files{i});
  [~, name] = fileparts(files{i});
  names(end+1) = name;
  plot(x(:,1), x(:,2));
endfor
legend(names);
xlabel("t");
ylabel("state variable");