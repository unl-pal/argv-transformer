#pragma once
#include "Vector.hpp"

class Rectangle {
public:
	float x;
	float y;
	float w;
	float h;

public:
	Rectangle(float x = 0, float y = 0, float w = 0, float h = 0);
	Rectangle(Vector<> p, float w = 0, float h = 0);
	Rectangle(float x, float y, Vector<> s);
	Rectangle(Vector<> p, Vector<> s);
	~Rectangle();

	bool contains(Vector<> p) const;
	float area() const;
};

