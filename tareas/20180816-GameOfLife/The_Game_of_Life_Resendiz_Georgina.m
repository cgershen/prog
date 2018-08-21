function varargout = The_Game_of_Life_Resendiz_Georgina(varargin)
% The_Game_of_Life_Resendiz_Georgina MATLAB code for The_Game_of_Life_Resendiz_Georgina.fig
%      The_Game_of_Life_Resendiz_Georgina, by itself, creates a new The_Game_of_Life_Resendiz_Georgina or raises the existing
%      singleton*.
%
%      H = The_Game_of_Life_Resendiz_Georgina returns the handle to a new The_Game_of_Life_Resendiz_Georgina or the handle to
%      the existing singleton*.
%
%      The_Game_of_Life_Resendiz_Georgina('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in The_Game_of_Life_Resendiz_Georgina.M with the given input arguments.
%
%      The_Game_of_Life_Resendiz_Georgina('Property','Value',...) creates a new The_Game_of_Life_Resendiz_Georgina or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before The_Game_of_Life_Resendiz_Georgina_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to The_Game_of_Life_Resendiz_Georgina_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help The_Game_of_Life_Resendiz_Georgina

% Last Modified by GUIDE v2.5 15-Aug-2018 21:22:10

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @The_Game_of_Life_Resendiz_Georgina_OpeningFcn, ...
                   'gui_OutputFcn',  @The_Game_of_Life_Resendiz_Georgina_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before The_Game_of_Life_Resendiz_Georgina is made visible.
function The_Game_of_Life_Resendiz_Georgina_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to The_Game_of_Life_Resendiz_Georgina (see VARARGIN)

% Choose default command line output for The_Game_of_Life_Resendiz_Georgina
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes The_Game_of_Life_Resendiz_Georgina wait for user response (see UIRESUME)
% uiwait(handles.figure1);

%Variables
condition=1; %condition for generations
handles.condition=condition; 
guidata(hObject, handles);

condition1=0; %condition1 for function draw
handles.condition=condition1;
guidata(hObject, handles);

j=100; %Number of generations
handles.j=j;
guidata(hObject, handles);

k=1; %Total Number of generations
handles.k=k;
guidata(hObject, handles);

image1=[];%To show the image in the matrix
handles.image1=image1;
guidata(hObject, handles);

A=[]; %Actual Matrix
handles.A=A;
guidata(hObject, handles);

B=[]; %Future Matrix
handles.B=B;
guidata(hObject, handles);

r=10; %Size of matrix
handles.r=r;
guidata(hObject, handles);

speed=20; %Speed
handles.speed=speed;
guidata(hObject, handles);

sigma1=2; %Sigma 1
handles.sigma1=sigma1;
guidata(hObject, handles);

sigma2=3; %Sigma 2
handles.sigma2=sigma2;
guidata(hObject, handles);

sigma3=3; %Sigma 3
handles.sigma3=sigma3;
guidata(hObject, handles);

cell_status_1=0; %Cell Status 1
handles.cell_status_1=cell_status_1;
guidata(hObject, handles);

cell_status_2=1; %Cell Status 2
handles.cell_status_2=cell_status_2;
guidata(hObject, handles);

cell_status_3=1; %Cell Status 3
handles.cell_status_3=cell_status_3;
guidata(hObject, handles);

cell_status_4=0; %Cell Status 5
handles.cell_status_4=cell_status_4;
guidata(hObject, handles);

density=0.5; %Density
handles.density=density;
guidata(hObject, handles);


%Initial random matrix

    handles.r=str2double(get(handles.size_of_matrix_edit,'String')); %Obtein size of matrix
    U=rand(handles.r,handles.r); %Obtein random matrix, with values between 0 and 1
    p=0.5;%Density
    handles.A=(U<p); %Obtein the Matrix of only 0's and 1's
    axes(handles.matrix_axes);
    handles.image1=imshow(handles.A); %Show matrix A in matrix_axes                         
    set(handles.num_living_cells_edit,'String',num2str(sum(sum(handles.A))));% Sum of all living cells (1's in matrix A) 
    handles.B=handles.A;    % Copy matrix 'A' to matrix 'B'
    guidata(hObject, handles);

  
% --- Outputs from this function are returned to the command line.
function varargout = The_Game_of_Life_Resendiz_Georgina_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes on button press in start_stop_togglebutton.
function start_stop_togglebutton_Callback(hObject, eventdata, handles)
% hObject    handle to start_stop_togglebutton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
    sum_neighbors=0;
    total_generations=handles.j;
    handles.k=0;

    if strcmp(get(handles.start_stop_togglebutton, 'String'),'START'); % Togglebutton "START" comparison
        set(handles.start_stop_togglebutton,'String','STOP');         % Change string to "STOP"
        %Enable-off elements
        set(handles.size_of_matrix_edit,'Enable','off');
        set(handles.num_generations_edit,'Enable','off');
        set(handles.random_matrix_togglebutton,'Enable','off');
        set(handles.clear_matrix_pushbutton,'Enable','off');
        set(handles.draw_living_cells_togglebutton,'Enable','off');
        set(handles.pattern_templates_menu,'Enable','off');
        set(handles.recognize_pushbutton,'Enable','off');
        set(handles.density_slider,'Enable','off');
        set(handles.change_rules_togglebutton,'Enable','off');
        set(handles.num_generations_edit,'String',0);
        pause(0.5);
        guidata(hObject, handles);
            
        while handles.k<=total_generations && get(handles.start_stop_togglebutton,'Value')==1
            for x=0:handles.r-1
                for y=0:handles.r-1
                    %Sum of all neighbors
                    sum_neighbors=handles.A(rem(x-1+handles.r,handles.r)+1,rem(y-1+handles.r,handles.r)+1)+handles.A(rem(x-1+handles.r,handles.r)+1,rem(y+handles.r,handles.r)+1)+handles.A(rem(x-1+handles.r,handles.r)+1,rem(y+1+handles.r,handles.r)+1)+handles.A(rem(x+handles.r,handles.r)+1,rem(y-1+handles.r,handles.r)+1)+handles.A(rem(x+handles.r,handles.r)+1,rem(y+1+handles.r,handles.r)+1)+handles.A(rem(x+1+handles.r,handles.r)+1,rem(y-1+handles.r,handles.r)+1)+handles.A(rem(x+1+handles.r,handles.r)+1,rem(y+handles.r,handles.r)+1)+handles.A(rem(x+1+handles.r,handles.r)+1,rem(y+1+handles.r,handles.r)+1);
                   
                    if handles.A(x+1,y+1)==1 %Living cell
                        if sum_neighbors<handles.sigma1 || sum_neighbors>handles.sigma2 % 2 or 3 living cells around
                            handles.B(x+1,y+1)=handles.cell_status_1;   % Cell dies
                        else               
                            handles.B(x+1,y+1)=handles.cell_status_2;   % Cell remains living
                        end
                    else  %Dead Cell
                        if sum_neighbors==handles.sigma3         % 3 living cells
                            handles.B(x+1,y+1)=handles.cell_status_3;   % Cell lives
                        else                
                            handles.B(x+1,y+1)=handles.cell_status_4;   % Cell remains dead
                        end
                    end
                    
                    guidata(hObject, handles);
                
                end
            end
            
             
            handles.A=handles.B; %Copy matrix B to A
            guidata(hObject, handles); %Update handles structure
        
            handles.image1=imshow(handles.A);
            guidata(hObject, handles); %Update handles structure
            
            set(handles.num_living_cells_edit,'String',num2str(sum(sum(handles.A))));
            set(handles.num_generations_edit,'String',handles.k);
            guidata(hObject, handles);  %Update handles structure
  
            refreshdata;
            drawnow;
         
            pause(1/get(handles.speed_slider,'Value')); %Speed between 1s-0.00002s
            handles.k=handles.k+1; %Increase by 1 to reach the total number of generations
            guidata(hObject, handles);
            
        end
        
           if handles.k==total_generations+1
              
                set(handles.start_stop_togglebutton,'String','START');%Change string to "START"
                %Enable-on elements
                set(handles.start_stop_togglebutton,'Value',0);
                set(handles.size_of_matrix_edit,'Enable','on');
                set(handles.num_generations_edit,'Enable','on');
                set(handles.random_matrix_togglebutton,'Enable','on');
                set(handles.clear_matrix_pushbutton,'Enable','on');
                set(handles.draw_living_cells_togglebutton,'Enable','on');
                set(handles.speed_slider,'Enable','on');
                handles.k=1;
                guidata(hObject, handles);
                
            end

    else                                 
                       
        set(handles.start_stop_togglebutton,'String','START'); %Change string to "START"                         
        guidata(hObject, handles);
        
        %Enable-on elements
        set(handles.num_generations_edit,'String',handles.j);
        set(handles.size_of_matrix_edit,'Enable','on');
        set(handles.num_generations_edit,'Enable','on');
        set(handles.random_matrix_togglebutton,'Enable','on');
        set(handles.clear_matrix_pushbutton,'Enable','on');
        set(handles.draw_living_cells_togglebutton,'Enable','on');
        set(handles.pattern_templates_menu,'Enable','on');
        set(handles.recognize_pushbutton,'Enable','on');
        set(handles.density_slider,'Enable','on');
        set(handles.change_rules_togglebutton,'Enable','on');
        guidata(hObject, handles);
       
    end
    


% --- Executes on slider movement.
function speed_slider_Callback(hObject, eventdata, handles)
% hObject    handle to speed_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
    s=get(handles.speed_slider,'Value'); %Obtein value of speed_slider
    handles.speed=1/s; %Convert obteined value between 1 - 100, to speed 1 to 2x10-5
    
    set(handles.text9,'String',handles.speed); 
    guidata(hObject, handles);



% --- Executes during object creation, after setting all properties.
function speed_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to speed_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end


% --- Executes on button press in random_matrix_togglebutton.
function random_matrix_togglebutton_Callback(hObject, eventdata, handles)
% hObject    handle to random_matrix_togglebutton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
  
if strcmp(get(handles.random_matrix_togglebutton, 'String'),'Random Matrix');%Togglebutton, String Random Matrix

set(handles.random_matrix_togglebutton,'String','Done');
set(handles.density_slider,'Enable','on');
set(handles.clear_matrix_pushbutton,'Enable','off');
set(handles.draw_living_cells_togglebutton,'Enable','off');
set(handles.pattern_templates_menu,'Enable','off');

%Generate Random Matrix
    
    while get(handles.random_matrix_togglebutton,'Value')==1
        handles.r=str2double(get(handles.size_of_matrix_edit,'String')); %Obtein size of matrix
        U=rand(handles.r,handles.r);    %Obtein random matrix, with values between 0 and 1
        handles.A=(U<=get(handles.density_slider,'Value')); %Obtein the Matrix of only 0's and 1's
        guidata(hObject, handles);
        handles.image1=imshow(handles.A); %Show matrix A in matrix_axes                         
        set(handles.num_living_cells_edit,'String',num2str(sum(sum(handles.A))));% Sum of all living cells (1's in matrix A)
        handles.B=handles.A;    % Copy matrix 'A' to matrix 'B'
        guidata(hObject, handles);
        pause(2);
    end

else
set(handles.random_matrix_togglebutton,'String','Random Matrix');%Togglebutton, string "Random Matrix"
set(handles.draw_living_cells_togglebutton,'Enable','on');
set(handles.clear_matrix_pushbutton,'Enable','on');
set(handles.density_slider,'Enable','off');
set(handles.pattern_templates_menu,'Enable','on');

end

guidata(hObject, handles);

    
  
% --- Executes on button press in clear_matrix_pushbutton.
function clear_matrix_pushbutton_Callback(hObject, eventdata, handles)
% hObject    handle to clear_matrix_pushbutton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
    guidata(hObject, handles); 
    %Clear matrix (zeros matrix)
    handles.r=str2double(get(handles.size_of_matrix_edit,'String')); %Obtein size of matrix
    handles.A=zeros(handles.r,handles.r); %Obtein zeros matrix 
    handles.image1=imshow(handles.A); %Show matrix A in matrix_axes    
    set(handles.num_living_cells_edit,'String',num2str(sum(sum(handles.A))));% Sum of all living cells (1's in matrix A)
    handles.B=handles.A;%Copy matrix 'A' to matrix 'B'
    
    guidata(hObject, handles);
  
    
% --- Executes on button press in draw_living_cells_togglebutton.
function draw_living_cells_togglebutton_Callback(hObject, eventdata, handles)
% hObject    handle to draw_living_cells_togglebutton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

%Draw Living Cells
guidata(hObject, handles);
imshow(handles.B);
 
if strcmp(get(handles.draw_living_cells_togglebutton, 'String'),'Draw Living Cells'); %Togglebutton 'Draw Living Cells'

set(handles.draw_living_cells_togglebutton,'String','Done');
set(handles.random_matrix_togglebutton,'Enable','off');
set(handles.clear_matrix_pushbutton,'Enable','off');

            [x, y]=ginput(1);       %To draw in the matrix
            x=round(x);            
            y=round(y);           
             
            handles.B(y,x)=1;
            guidata(hObject, handles);
            imshow(handles.B);
            
            handles.A=handles.B;
            guidata(hObject, handles);
            imshow(handles.A);
            
            set(handles.num_living_cells_edit,'String',num2str(sum(sum(handles.A))));% Sum of all living cells (1's in matrix A)

else
set(handles.draw_living_cells_togglebutton,'String','Draw Living Cells');
set(handles.random_matrix_togglebutton,'Enable','on');
set(handles.clear_matrix_pushbutton,'Enable','on');

end

guidata(hObject, handles);



function num_generations_edit_Callback(hObject, eventdata, handles)
% hObject    handle to num_generations_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of num_generations_edit as text
%        str2double(get(hObject,'String')) returns contents of num_generations_edit as a double

%Number of Generations 
num_generations=get(handles.num_generations_edit,'String');

%Size Limits between 0 and 10000 (only positive integer numbers)
if (str2double(num_generations)<0)||(str2double(num_generations)>10000)||(str2double(num_generations)~=round(str2double(num_generations)))||(isempty(str2num(num_generations))) %Limit to positive integers
    errordlg('Please type a positive integer number (<10000).','Error')   
    set(handles.num_generations_edit,'String',handles.j);
else
    handles.j=str2double(get(handles.num_generations_edit,'String'));%Set value of handles.num_generations_edit to handles.j
end

guidata(hObject, handles);


% --- Executes during object creation, after setting all properties.
function num_generations_edit_CreateFcn(hObject, eventdata, handles)
% hObject    handle to num_generations_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function size_of_matrix_edit_Callback(hObject, eventdata, handles)
% hObject    handle to size_of_matrix_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of size_of_matrix_edit as text
%        str2double(get(hObject,'String')) returns contents of size_of_matrix_edit as a double

size_of_matrix=get(handles.size_of_matrix_edit,'String');

%Size Limits between 1 and 10000 (only positive integer numbers)
if (str2double(size_of_matrix)<1)||(str2double(size_of_matrix)>10000)||(str2double(size_of_matrix)~=round(str2double(size_of_matrix)))||(isempty(str2num(size_of_matrix))) %Limit to positive integers
    errordlg('Please type a positive integer number (>0 && <=10000).','Error')   
    set(handles.size_of_matrix_edit,'String',handles.r);
else %Show the random matrix with a valid size
    handles.r=str2double(size_of_matrix); %Obtein size of matrix
    U=rand(handles.r,handles.r);    %Obtein random matrix, with values between 0 and 1
    p=0.5;%Density
    handles.A=(U<p); %Obtein the Matrix of only 0's and 1's
    axes(handles.matrix_axes); %Use matrix_axes to show matrix
    handles.image1=imshow(handles.A); %Show matrix A                         
    set(handles.num_living_cells_edit,'String',num2str(sum(sum(handles.A))));% Sum of all living cells (1's in matrix A)                                     
    handles.B=handles.A;    % Copy matrix 'A' to matrix 'B'
end

guidata(hObject, handles); 


% --- Executes during object creation, after setting all properties.
function size_of_matrix_edit_CreateFcn(hObject, eventdata, handles)
% hObject    handle to size_of_matrix_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in generate_graph_pushbutton.
function generate_graph_pushbutton_Callback(hObject, eventdata, handles)
% hObject    handle to generate_graph_pushbutton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on button press in exit_pushbutton.
function exit_pushbutton_Callback(hObject, eventdata, handles)
% hObject    handle to exit_pushbutton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
   
    
     answer=questdlg('Do you want to quit "The Game of Life"?','The Game of Life'); %Exit the game
     if strcmp(answer,'Yes')
        
        %Clear the command window
        clc;
   
        %Clear the variables
        clearStr='clear all';
        evalin('base',clearStr);
        
        %Delete the figure
        delete(handles.figure1);
        
     end
    
 
function num_living_cells_edit_Callback(hObject, eventdata, handles)
% hObject    handle to num_living_cells_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of num_living_cells_edit as text
%        str2double(get(hObject,'String')) returns contents of num_living_cells_edit as a double


% --- Executes during object creation, after setting all properties.
function num_living_cells_edit_CreateFcn(hObject, eventdata, handles)
% hObject    handle to num_living_cells_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end

% % --- Executes on button press in save_changes_pushbutton.
% function save_changes_pushbutton_Callback(hObject, eventdata, handles)
% % hObject    handle to save_changes_pushbutton (see GCBO)
% % eventdata  reserved - to be defined in a future version of MATLAB
% % handles    structure with handles and user data (see GUIDATA)
% 
% 
% % --- Executes on button press in cancel_pushbutton.
% function cancel_pushbutton_Callback(hObject, eventdata, handles)
% % hObject    handle to cancel_pushbutton (see GCBO)
% % eventdata  reserved - to be defined in a future version of MATLAB
% % handles    structure with handles and user data (see GUIDATA)


% --- Executes on selection change in pattern_templates_menu.
function pattern_templates_menu_Callback(hObject, eventdata, handles)
% hObject    handle to pattern_templates_menu (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = cellstr(get(hObject,'String')) returns pattern_templates_menu contents as cell array
%        contents{get(hObject,'Value')} returns selected item from pattern_templates_menu

%Pattern Templates
set(handles.size_of_matrix_edit,'Enable','off');
guidata(hObject, handles);
menu=get(hObject,'String');
a=get(hObject,'Value');
option=menu(a);

switch cell2mat(option)

    case '1. Still Lifes'
        handles.r=15;
        set(handles.size_of_matrix_edit,'String','15');
        handles.j=str2double(get(handles.num_generations_edit,'String'));
        guidata(hObject, handles);
        
        handles.A=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,1,1,0,0,0,1,1,0,0,0,1,1,0,0;  
                   0,1,1,0,0,1,0,0,1,0,0,1,0,1,0; 
                   0,0,0,0,0,0,1,1,0,0,0,0,1,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,1,1,0,0,0,0,0,0,1,0,0; 
                   0,0,0,1,0,0,1,0,0,0,0,1,0,1,0;  
                   0,0,0,0,1,0,1,0,0,0,0,0,1,0,0;
                   0,0,0,0,0,1,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
               
    case '2. Oscillators'
        handles.r=30;
        set(handles.size_of_matrix_edit,'String','30');
        guidata(hObject, handles);
        
        handles.A=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
        
    case '3. Spaceships'
        handles.r=30;
        set(handles.size_of_matrix_edit,'String','30');
        guidata(hObject, handles);
        
        handles.A=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
    case '4. Methuselahs'
        handles.r=30;
        set(handles.size_of_matrix_edit,'String','30');
        guidata(hObject, handles);
        handles.A=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
    case '5. Guns'
        handles.r=40;
        set(handles.size_of_matrix_edit,'String','40');
        guidata(hObject, handles);
        handles.A=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0;  
                   0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;  
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0;
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
    case '6. Pattern for recognizing'
        handles.r=4;
        set(handles.size_of_matrix_edit,'String','4');
        handles.j=str2double(get(handles.num_generations_edit,'String'));
        guidata(hObject, handles);
        
        handles.A=[0,0,0,0;
                   0,1,1,0;
                   0,1,1,0
                   0,0,0,0];
               
                 
end

        axes(handles.matrix_axes);
        handles.image1=imshow(handles.A); %Show matrix A in matrix_axes                         
        set(handles.num_living_cells_edit,'String',num2str(sum(sum(handles.A))));% Sum of all living cells (1's in matrix A)
        handles.B=handles.A;    % Copy matrix 'A' to matrix 'B'
        guidata(hObject, handles);




% --- Executes during object creation, after setting all properties.
function pattern_templates_menu_CreateFcn(hObject, eventdata, handles)
% hObject    handle to pattern_templates_menu (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: popupmenu controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function sigma1_edit_Callback(hObject, eventdata, handles)
% hObject    handle to sigma1_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of sigma1_edit as text
%        str2double(get(hObject,'String')) returns contents of sigma1_edit as a double

%Value sigma 1
sigma1=get(handles.sigma1_edit,'String');

if (str2double(sigma1)<0)||(str2double(sigma1)>8)||(str2double(sigma1)~=round(str2double(sigma1)))||(isempty(str2num(sigma1))) %Limit to positive integers
    errordlg('Please type a positive integer number (<=8).','Error')   
    set(handles.sigma1_edit,'String',2);
else 
    handles.sigma1=str2double(get(handles.sigma1_edit,'String'));
end

guidata(hObject, handles); 

    


% --- Executes during object creation, after setting all properties.
function sigma1_edit_CreateFcn(hObject, eventdata, handles)
% hObject    handle to sigma1_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function sigma2_edit_Callback(hObject, eventdata, handles)
% hObject    handle to sigma2_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of sigma2_edit as text

%Value sigma 2
sigma2=get(handles.sigma2_edit,'String');

if (str2double(sigma2)<0)||(str2double(sigma2)>8)||(str2double(sigma2)~=round(str2double(sigma2)))||(isempty(str2num(sigma2))) %Limit to positive integers
    errordlg('Please type a positive integer number (<=8).','Error')   
    set(handles.sigma2_edit,'String',3);
else 
    handles.sigma2=str2double(get(handles.sigma2_edit,'String'));
end

guidata(hObject, handles); 


% --- Executes during object creation, after setting all properties.
function sigma2_edit_CreateFcn(hObject, eventdata, handles)
% hObject    handle to sigma2_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function sigma3_edit_Callback(hObject, eventdata, handles)
% hObject    handle to sigma3_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of sigma3_edit as text
%        str2double(get(hObject,'String')) returns contents of sigma3_edit as a double

%Value sigma3
sigma3=get(handles.sigma3_edit,'String');

if (str2double(sigma3)<0)||(str2double(sigma3)>8)||(str2double(sigma3)~=round(str2double(sigma3)))||(isempty(str2num(sigma3))) %Limit to positive integers
    errordlg('Please type a positive integer number (<=8).','Error')   
    set(handles.sigma3_edit,'String',3);
  
else 
    handles.sigma3=str2double(get(handles.sigma3_edit,'String'));
end

guidata(hObject, handles); %Actualizar valores A y B

% --- Executes during object creation, after setting all properties.
function sigma3_edit_CreateFcn(hObject, eventdata, handles)
% hObject    handle to sigma3_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in change_rules_togglebutton.
function change_rules_togglebutton_Callback(hObject, eventdata, handles)
% hObject    handle to change_rules_togglebutton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

if strcmp(get(handles.change_rules_togglebutton, 'String'),'Change Rules'); %Togglebutton string "Change Rules"
set(handles.change_rules_togglebutton,'String','Done');%Change String to "Done"
%Enable-on elements
set(handles.sigma1_edit,'Enable','on');
set(handles.sigma2_edit,'Enable','on');
set(handles.sigma3_edit,'Enable','on');
set(handles.cell_status_1_edit,'Enable','on');
set(handles.cell_status_2_edit,'Enable','on');

else
set(handles.change_rules_togglebutton,'String','Change Rules');%Change String to "Change Rules"
%Enable-off elements
set(handles.sigma1_edit,'Enable','off');
set(handles.sigma2_edit,'Enable','off');
set(handles.sigma3_edit,'Enable','off');
set(handles.cell_status_1_edit,'Enable','off');
set(handles.cell_status_2_edit,'Enable','off');
end

guidata(hObject, handles); 





function cell_status_1_edit_Callback(hObject, eventdata, handles)
% hObject    handle to cell_status_1_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of cell_status_1_edit as text
%        str2double(get(hObject,'String')) returns contents of cell_status_1_edit as a double

%Cell status 1
cell_status_1=get(handles.cell_status_1_edit,'String');

if (str2double(cell_status_1)<0)||(str2double(cell_status_1)>1)||(str2double(cell_status_1)~=round(str2double(cell_status_1)))||(isempty(str2num(cell_status_1))) %Limit to positive integers
    errordlg('Please type only 0 (Cell dies) or 1 (Cell lives).','Error')   
    set(handles.cell_status_1_edit,'String',0);
    handles.cell_status_1==0;
else 
    handles.cell_status_1=str2double(get(handles.cell_status_1_edit,'String'));
    
    if handles.cell_status_1==0
        set(handles.cell_status_1_text,'String','Cell dies');
        handles.cell_status_2=1;
    else
        set(handles.cell_status_1_text,'String','Cell lives');
        handles.cell_status_2=0;
    end
    
end

guidata(hObject, handles); 

% --- Executes during object creation, after setting all properties.
function cell_status_1_edit_CreateFcn(hObject, eventdata, handles)
% hObject    handle to cell_status_1_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function cell_status_2_edit_Callback(hObject, eventdata, handles)
% hObject    handle to cell_status_2_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of cell_status_2_edit as text
%        str2double(get(hObject,'String')) returns contents of cell_status_2_edit as a double

%Cell status 2
cell_status_2=get(handles.cell_status_2_edit,'String');

if (str2double(cell_status_2)<0)||(str2double(cell_status_2)>1)||(str2double(cell_status_2)~=round(str2double(cell_status_2)))||(isempty(str2num(cell_status_2))) %Limit to positive integers
    errordlg('Please type only 0 (Cell dies) or 1 (Cell lives).','Error')   
    set(handles.cell_status_2_edit,'String',1);
    handles.cell_status_3==1;
    
else 
    handles.cell_status_3=str2double(get(handles.cell_status_2_edit,'String'));
    
    if handles.cell_status_3==0
        set(handles.cell_status_2_text,'String','Cell dies');
        handles.cell_status_4=1;
    else
        set(handles.cell_status_2_text,'String','Cell lives');
        handles.cell_status_4=0;
    end
    
end

guidata(hObject, handles); 


% --- Executes during object creation, after setting all properties.
function cell_status_2_edit_CreateFcn(hObject, eventdata, handles)
% hObject    handle to cell_status_2_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on slider movement.
function density_slider_Callback(hObject, eventdata, handles)
% hObject    handle to density_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider
% set(handles.density_slider,'Enable','off');

d=get(handles.density_slider,'Value'); %Obtein value of density_slider
handles.density=d; 
    
set(handles.density_slider_text,'String',handles.density); 
guidata(hObject, handles);

    guidata(hObject, handles);


% --- Executes during object creation, after setting all properties.
function density_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to density_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end



function still_lifes_edit_Callback(hObject, eventdata, handles)
% hObject    handle to still_lifes_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of still_lifes_edit as text
%        str2double(get(hObject,'String')) returns contents of still_lifes_edit as a double


% --- Executes during object creation, after setting all properties.
function still_lifes_edit_CreateFcn(hObject, eventdata, handles)
% hObject    handle to still_lifes_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function oscillators_edit_Callback(hObject, eventdata, handles)
% hObject    handle to oscillators_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of oscillators_edit as text
%        str2double(get(hObject,'String')) returns contents of oscillators_edit as a double


% --- Executes during object creation, after setting all properties.
function oscillators_edit_CreateFcn(hObject, eventdata, handles)
% hObject    handle to oscillators_edit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in recognize_pushbutton.
function recognize_pushbutton_Callback(hObject, eventdata, handles)
% hObject    handle to recognize_pushbutton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

%Recognition Still Lifes Patter Algorithm
sum_neighbors=0;

sum_still_life_pattern=0; %Count until 4 times, 3 neighbors
num_still_life_patterns=0;

for x=0:handles.r-1
                for y=0:handles.r-1
                    %Sum of all neighbors
                    sum_neighbors=handles.A(rem(x-1+handles.r,handles.r)+1,rem(y-1+handles.r,handles.r)+1)+handles.A(rem(x-1+handles.r,handles.r)+1,rem(y+handles.r,handles.r)+1)+handles.A(rem(x-1+handles.r,handles.r)+1,rem(y+1+handles.r,handles.r)+1)+handles.A(rem(x+handles.r,handles.r)+1,rem(y-1+handles.r,handles.r)+1)+handles.A(rem(x+handles.r,handles.r)+1,rem(y+1+handles.r,handles.r)+1)+handles.A(rem(x+1+handles.r,handles.r)+1,rem(y-1+handles.r,handles.r)+1)+handles.A(rem(x+1+handles.r,handles.r)+1,rem(y+handles.r,handles.r)+1)+handles.A(rem(x+1+handles.r,handles.r)+1,rem(y+1+handles.r,handles.r)+1);
                    disp(sum_neighbors);
                    if sum_neighbors==3
                        sum_still_life_pattern=sum_still_life_pattern+1;
                        
                        if sum_still_life_pattern==4;
                            num_still_life_patterns=num_still_life_patterns+1;
                            set(handles.still_lifes_edit,'String',num_still_life_patterns);
                            guidata(hObject, handles);
                        end
                        
                    end
                    
                end
end

                        
