// Color
def red(message) {
    echo "\033[1;31m${message} \033[0m"
}

def green(message) {
    echo "\033[1;32m${message} \033[0m"
}

def blue(message) {
    echo "\033[1;34m${message} \033[0m"
}

def yellow(message) {
    echo "\033[1;33m${message} \033[0m"
}

// Log
def title(message) {
    blue "[INFO] >>> ${message}"
}

def info(message) {
    green "[INFO] ${message}"
}

def warn(message) {
    yellow "[WARN] ${message}"
}

def error(message) {
    red "[ERROR] ${message}"
}