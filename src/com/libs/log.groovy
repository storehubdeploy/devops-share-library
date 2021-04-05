package com.libs



// Color
void red(message) {
    echo "\033[1;31m${message} \033[0m"
}

void green(message) {
    echo "\033[1;32m${message} \033[0m"
}

void blue(message) {
    echo "\033[1;34m${message} \033[0m"
}

void yellow(message) {
    echo "\033[1;33m${message} \033[0m"
}

// Log
void title(message) {
    blue ">>> ${message}"
}

void info(message) {
    green "[INFO] ${message}"
}

void warn(message) {
    yellow "[WARN] ${message}"
}

void error(message) {
    red "[ERROR] ${message}"
}

