package org.pondar.pacmankotlin.Engine.Collections

import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D

enum class Orientations(val Direction: Vector2D) {
    Up(Vector2D(0F,-1F)),
    Right(Vector2D(1F, 0F)),
    Down(Vector2D(0F, 1F)),
    Left(Vector2D(-1F, 0F)),
    DiagonalUR(Vector2D(0.5F, 0.5F)),
    DiagonalUL(Vector2D(-0.5F, 0.5F)),
    DiagonalBR(Vector2D(0.5F, -0.5F)),
    DiagonalBL(Vector2D(-0.5F, -0.5F)),
    Both(Vector2D()),
}