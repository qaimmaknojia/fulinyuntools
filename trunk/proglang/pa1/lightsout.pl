:-dynamic cell/3.

% Since pressing the same cell twice is equivalent to doing nothing,
% every cell is either not pressed or pressed only once.
press(1,1,0).
press(1,2,0).
press(1,3,0).
press(1,4,0).
press(1,5,0).
press(2,1,0).
press(2,2,0).
press(2,3,0).
press(2,4,0).
press(1,5,0).
press(3,1,0).
press(3,2,0).
press(3,3,0).
press(3,4,0).
press(3,5,0).
press(4,1,0).
press(4,2,0).
press(4,3,0).
press(4,4,0).
press(4,5,0).
press(5,1,0).
press(5,2,0).
press(5,3,0).
press(5,4,0).
press(5,5,0).
press(1,1,1).
press(1,2,1).
press(1,3,1).
press(1,4,1).
press(1,5,1).
press(2,1,1).
press(2,2,1).
press(2,3,1).
press(2,4,1).
press(1,5,1).
press(3,1,1).
press(3,2,1).
press(3,3,1).
press(3,4,1).
press(3,5,1).
press(4,1,1).
press(4,2,1).
press(4,3,1).
press(4,4,1).
press(4,5,1).
press(5,1,1).
press(5,2,1).
press(5,3,1).
press(5,4,1).
press(5,5,1).

% Keeping naming consistent, please use the name "load" to read the file into the board and "solve" to actually solve the game.
load(File):-
	seeing(Old),       % save for later
	see(File),         % open this file
	get25(1),	   % read 25 characters from File
	seen,              % close File
	see(Old),	   % previous read source
	writeboard(1,1),!. % write the board

get25(26):-!.              % read only 25 characters
get25(N):-get0(X1),	   % read one character
	trans(X1,X),       % translate the ascii into 'B' or 'W'
	n2coord(N,Row,Column), % get the row and column numbers
	assert(cell(Row,Column,X)), % load the status of a cell
	N1 is N+1,get25(N1). % go on reading

% The N-th character is at row (N-1)//5+1 and column (N-1) mod 5 + 1
n2coord(N, Row, Column):-T1 is N-1,T2 is T1//5,Row is T2+1,T3 is mod(T1,5),Column is T3+1.

trans(66,'B'). % black Ascii('B')=66
trans(87,'W'). % white Ascii('W')=87

writeboard(5,6):-nl,!. % all 25 cells already written
writeboard(X,6):-nl, X1 is X+1, writeboard(X1,1). % reach the end of one row, go on with the next row
writeboard(X,Y):-cell(X,Y,C),write(C), % write the status of one cell
	Y1 is Y+1,writeboard(X,Y1). % go on writing

% Keeping naming consistent, please use the name "load" to read the file into the board and "solve" to actually solve the game.
% Get the loaded assertions and pass the board status to solve/50
solve:-cell(1,1,C11),cell(1,2,C12),cell(1,3,C13),cell(1,4,C14),cell(1,5,C15),
	cell(2,1,C21),cell(2,2,C22),cell(2,3,C23),cell(2,4,C24),cell(2,5,C25),
	cell(3,1,C31),cell(3,2,C32),cell(3,3,C33),cell(3,4,C34),cell(3,5,C35),
	cell(4,1,C41),cell(4,2,C42),cell(4,3,C43),cell(4,4,C44),cell(4,5,C45),
	cell(5,1,C51),cell(5,2,C52),cell(5,3,C53),cell(5,4,C54),cell(5,5,C55),
	solve(cell(1,1,C11),cell(1,2,C12),cell(1,3,C13),cell(1,4,C14),cell(1,5,C15),
	      cell(2,1,C21),cell(2,2,C22),cell(2,3,C23),cell(2,4,C24),cell(2,5,C25),
	      cell(3,1,C31),cell(3,2,C32),cell(3,3,C33),cell(3,4,C34),cell(3,5,C35),
	      cell(4,1,C41),cell(4,2,C42),cell(4,3,C43),cell(4,4,C44),cell(4,5,C45),
	      cell(5,1,C51),cell(5,2,C52),cell(5,3,C53),cell(5,4,C54),cell(5,5,C55),
	      press(1,1,N11),press(1,2,N12),press(1,3,N13),press(1,4,N14),press(1,5,N15),
	      press(2,1,N21),press(2,2,N22),press(2,3,N23),press(2,4,N24),press(1,5,N25),
	      press(3,1,N31),press(3,2,N32),press(3,3,N33),press(3,4,N34),press(3,5,N35),
	      press(4,1,N41),press(4,2,N42),press(4,3,N43),press(4,4,N44),press(4,5,N45),
	      press(5,1,N51),press(5,2,N52),press(5,3,N53),press(5,4,N54),press(5,5,N55)),
	% write down the cells pressed.
	(N11 is 1 -> write('1,1'),nl ; write('')),
	(N12 is 1 -> write('1,2'),nl ; write('')),
	(N13 is 1 -> write('1,3'),nl ; write('')),
	(N14 is 1 -> write('1,4'),nl ; write('')),
	(N15 is 1 -> write('1,5'),nl ; write('')),
	(N21 is 1 -> write('2,1'),nl ; write('')),
	(N22 is 1 -> write('2,2'),nl ; write('')),
	(N23 is 1 -> write('2,3'),nl ; write('')),
	(N24 is 1 -> write('2,4'),nl ; write('')),
	(N25 is 1 -> write('2,5'),nl ; write('')),
	(N31 is 1 -> write('3,1'),nl ; write('')),
	(N32 is 1 -> write('3,2'),nl ; write('')),
	(N33 is 1 -> write('3,3'),nl ; write('')),
	(N34 is 1 -> write('3,4'),nl ; write('')),
	(N35 is 1 -> write('3,5'),nl ; write('')),
	(N41 is 1 -> write('4,1'),nl ; write('')),
	(N42 is 1 -> write('4,2'),nl ; write('')),
	(N43 is 1 -> write('4,3'),nl ; write('')),
	(N44 is 1 -> write('4,4'),nl ; write('')),
	(N45 is 1 -> write('4,5'),nl ; write('')),
	(N51 is 1 -> write('5,1'),nl ; write('')),
	(N52 is 1 -> write('5,2'),nl ; write('')),
	(N53 is 1 -> write('5,3'),nl ; write('')),
	(N54 is 1 -> write('5,4'),nl ; write('')),
	(N55 is 1 -> write('5,5'),nl ; write('')).

% Find what cells to press in order to turn all the lights off.
% cell(1,1,C11)...cell(5,5,C55) is the initial status of the board.
solve(cell(1,1,C11),cell(1,2,C12),cell(1,3,C13),cell(1,4,C14),cell(1,5,C15),
      cell(2,1,C21),cell(2,2,C22),cell(2,3,C23),cell(2,4,C24),cell(2,5,C25),
      cell(3,1,C31),cell(3,2,C32),cell(3,3,C33),cell(3,4,C34),cell(3,5,C35),
      cell(4,1,C41),cell(4,2,C42),cell(4,3,C43),cell(4,4,C44),cell(4,5,C45),
      cell(5,1,C51),cell(5,2,C52),cell(5,3,C53),cell(5,4,C54),cell(5,5,C55),
      press(1,1,N11),press(1,2,N12),press(1,3,N13),press(1,4,N14),press(1,5,N15),
      press(2,1,N21),press(2,2,N22),press(2,3,N23),press(2,4,N24),press(1,5,N25),
      press(3,1,N31),press(3,2,N32),press(3,3,N33),press(3,4,N34),press(3,5,N35),
      press(4,1,N41),press(4,2,N42),press(4,3,N43),press(4,4,N44),press(4,5,N45),
      press(5,1,N51),press(5,2,N52),press(5,3,N53),press(5,4,N54),press(5,5,N55)):-
	% only try pressing each cell at most once
	press(1,1,N11),press(1,2,N12),press(1,3,N13),press(1,4,N14),press(1,5,N15),
	press(2,1,N21),press(2,2,N22),press(2,3,N23),press(2,4,N24),press(1,5,N25),
	press(3,1,N31),press(3,2,N32),press(3,3,N33),press(3,4,N34),press(3,5,N35),
	press(4,1,N41),press(4,2,N42),press(4,3,N43),press(4,4,N44),press(4,5,N45),
	press(5,1,N51),press(5,2,N52),press(5,3,N53),press(5,4,N54),press(5,5,N55),
	% if all the lights are out after the pressings, we find a solution
	black(C11,N11,N12,N21),
	black(C12,N11,N12,N13,N22),
	black(C13,N12,N13,N14,N23),
	black(C14,N13,N14,N15,N24),
	black(C15,N14,N15,N25),
	black(C21,N11,N21,N22,N31),
	black(C22,N12,N21,N22,N23,N32),
	black(C23,N13,N22,N23,N24,N33),
	black(C24,N14,N23,N24,N25,N34),
	black(C25,N15,N24,N25,N35),
	black(C31,N21,N31,N32,N41),
	black(C32,N22,N31,N32,N33,N42),
	black(C33,N23,N32,N33,N34,N43),
	black(C34,N24,N33,N34,N35,N44),
	black(C35,N25,N34,N35,N45),
	black(C41,N31,N41,N42,N51),
	black(C42,N32,N41,N42,N43,N52),
	black(C43,N33,N42,N43,N44,N53),
	black(C44,N34,N43,N44,N45,N54),
	black(C45,N35,N44,N45,N55),
	black(C51,N41,N51,N52),
	black(C52,N42,N51,N52,N53),
	black(C53,N43,N52,N53,N54),
	black(C54,N44,N53,N54,N55),
	black(C55,N45,N54,N55).

% whether a light will be out after 3, 4 or five pressings nearby
black(C,P1,P2,P3):-T1 is P1+P2, T2 is T1+P3, (C = 'B' -> 0 is mod(T2,2) ; 1 is mod(T2,2)).
black(C,P1,P2,P3,P4):-T1 is P1+P2, T2 is T1+P3, T3 is T2+P4, (C = 'B' -> 0 is mod(T3,2) ; 1 is mod(T3,2)).
black(C,P1,P2,P3,P4,P5):-T1 is P1+P2, T2 is T1+P3, T3 is T2+P4, T4 is T3+P5, (C = 'B' -> 0 is mod(T4,2) ; 1 is mod(T4,2)).
















