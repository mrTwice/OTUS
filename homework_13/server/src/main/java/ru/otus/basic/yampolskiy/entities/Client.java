package ru.otus.basic.yampolskiy.entities;

import java.net.Socket;

public record Client(User user, Socket socket) {
}
