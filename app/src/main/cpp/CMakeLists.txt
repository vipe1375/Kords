cmake_minimum_required(VERSION 3.22)
project(app)

add_link_options("LINKER:--build-id=none")

find_package(oboe REQUIRED CONFIG)

get_target_property(OBOE_INC oboe::oboe INTERFACE_INCLUDE_DIRECTORIES)
get_target_property(OBOE_LIB oboe::oboe IMPORTED_LOCATION)
if(NOT OBOE_LIB)
    get_target_property(OBOE_LIB oboe::oboe IMPORTED_LOCATION_RELEASE)
endif()

set(oboe_INCLUDE_DIR "${OBOE_INC}" CACHE PATH "" FORCE)
set(_oboe_library    "${OBOE_LIB}" CACHE FILEPATH "" FORCE)
message(STATUS ">>> OBOE_INC=${OBOE_INC}")
message(STATUS ">>> OBOE_LIB=${OBOE_LIB}")

set(BUILD_SHARED_LIBS ON CACHE BOOL "" FORCE)
set(enable-oboe ON CACHE BOOL "" FORCE)
set(enable-dbus OFF CACHE BOOL "" FORCE)
set(enable-jack OFF CACHE BOOL "" FORCE)
set(enable-alsa OFF CACHE BOOL "" FORCE)
set(enable-readline OFF CACHE BOOL "" FORCE)

set(GCEM_INCLUDE_DIR "${CMAKE_CURRENT_SOURCE_DIR}/fluidsynth/gcem/include" CACHE PATH "" FORCE)
add_subdirectory(fluidsynth)

add_library(native-lib SHARED native-lib.c)
target_link_libraries(native-lib libfluidsynth OpenSLES log oboe::oboe)
