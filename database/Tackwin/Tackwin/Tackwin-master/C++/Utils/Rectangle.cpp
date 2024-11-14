#include "Rectangle.hpp"

Rectangle::Rectangle(float x, float y, float w, float h) : x(x), y(y), w(w), h(h) {
}
Rectangle::Rectangle(Vector p, float w, float h) : x(p.x), y(p.y), w(w), h(h) {
}
Rectangle::Rectangle(float x, float y, Vector s) : x(x), y(y), w(s.x), h(s.y) {
}
Rectangle::Rectangle(Vector p, Vector s) : x(p.x), y(p.y), w(s.x), h(s.y) {
}

Rectangle::~Rectangle() {
}

bool Rectangle::contains(Vector<> p) const {
	return  x < p.x && p.x < x + h &&
		    y < p.y && p.y < y + h;
}

float Rectangle::area() const {
	return w * h;
}
