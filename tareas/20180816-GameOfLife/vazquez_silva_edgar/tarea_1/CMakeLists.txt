cmake_minimum_required(VERSION 2.8)
project(life_game)
find_package(OpenCV REQUIRED)

####   INLUDES  ######
include_directories(include)

### SET-SOURCES  ####
set(SOURCES src/test_1.cpp)

### ADD-EXECUTABLE  ####
add_executable(lifeGame ${SOURCES})

### LIBRARIES-OPENCV  ###
target_link_libraries(lifeGame ${OpenCV_LIBS})
