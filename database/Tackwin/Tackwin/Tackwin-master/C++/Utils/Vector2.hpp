#pragma once
#include <cmath>
#include <string>
#include <SFML/Graphics.hpp>


/// IS ONLY WELL DEFINED FOR FLOAT AND DOUBLE
template<typename T = float>
struct Vector2 {
	static const Vector2<float> ZERO;
	static const Vector2<float> ZEROf;
	static const Vector2<double> ZEROd;
	static Vector2 createUnitVector(T a){return { std::cosf(a), std::sinf(a)}; }

	T x = 0;
	T y = 0;

	Vector2(const Vector2 &vector) : x(vector.x), y(vector.y) {}
	Vector2(const T &x = 0, const T &y = 0) : x(x), y(y) {};
	~Vector2() {};

	inline Vector2& get() { return *this; }

	T length2() const {
		return x * x + y * y;
	};
	T length() const {
		return std::sqrt(length2());
	};

	T distTo2(const T &x, const T &y) const {
		return (this->x - x) * (this->x - x) + (this->y - y) * (this->y - y);
	};
	T distTo2(const Vector2 &A) const {
		return (this->x - A.x) * (this->x - A.x) + (this->y - A.y) * (this->y - A.y);
	};
	T distTo(const T &x, const T &y) const {
		return std::sqrt(distTo2(x, y));
	};
	T distTo(const Vector2 &A) const {
		return std::sqrt(distTo2(A));
	};

	T angleX() const {
		return std::atan2(y, x);
	};
	T angleTo(const T &x, const T &y) const {
		return std::atan2(y - this->y, x - this->x);
	};
	T angleTo(const Vector2 &A) const {
		return std::atan2(A.y - y, A.x - x);
	};

	Vector2 getUnit() {
		T l = length();
		return Vector2(x / l, y / l);
	};

	Vector2& normalize(T l = 1.f) {
		x /= l;
		y /= l;
		return *this;
	};

	Vector2& rotate(T rad) {
		T tx = x;
		T tY = y;
		x = tX * std::cos(rad) - tY * std::sin(rad);
		y = tX * std::sin(rad) + tY * std::cos(rad);
		return *this;
	};
	Vector2& rotTo(T rad) {
		return rotate(rad - angleX());
	};
	Vector2& rotate90() {
		T tmpX = x;
		x = y;
		y = tmpX;
		return *this;
	};

	Vector2& operator=(const Vector2<T> &A) {
		x = static_cast<float>(A.x);
		y = static_cast<float>(A.y);
		return *this;
	};

	Vector2 operator+(const T &A) const {
		return Vector2(x + A, y + A);
	};
	Vector2 operator+(const Vector2<T> &A) const {
		return Vector2(x + A.x, y + A.y);
	};
	Vector2& operator+=(const T &A) {
		x += A;
		y += A;
		return *this;
	};
	Vector2& operator+=(const Vector2<T> &A) {
		x += A.x;
		y += A.y;
		return *this;
	};

	Vector2 operator-(const T &A) const {
		return Vector2(x - A, y - A);
	};
	Vector2 operator-(const Vector2<T> &A) const {
		return Vector2(x - A.x, y - A.y);
	};
	Vector2& operator-=(const Vector2<T> &A) {
		x -= A.x;
		y -= A.y;
		return *this;
	};

	Vector2 operator*(const T &A) const {
		return Vector2(x * static_cast<const float>(A), y * static_cast<const float>(A));
	};
	Vector2& operator*=(const T &A) {
		x *= A;
		y *= A;
		return *this;
	};

	Vector2 operator/(const T &A) const {
		return Vector2(x / A, y / A);
	};
	Vector2& operator/=(const T &A) {
		x /= A;
		y /= A;
		return *this;
	};

	bool operator==(const Vector2 &A) const {return A.x == x && A.y == y;};
	bool operator!=(const Vector2 &A) const {return !(*this == A);};
	bool operator>(const Vector2 &A) const  {return length2() > A.length2();};
	bool operator>=(const Vector2 &A) const {return length2() >= A.length2();};
	bool operator<(const Vector2 &A) const  {return length2() < A.length2();};
	bool operator<=(const Vector2 &A) const {return length2() <= A.length2();};


	template<typename T>
	static T det(const Vector2<T> &A, const Vector2<T> &B) {
		return A.x * B.y - A.y * B.x;
	};

	template<typename T>
	static bool intersect(const Vector2<T> &p0, const Vector2<T> &p1, const Vector2<T> &p2, const Vector2<T> &p3, Vector2<T>* i = nullptr) {
		T s1_x, s1_y, s2_x, s2_y;
		s1_x = p1.x - p0.x;     s1_y = p1.y - p0.y;
		s2_x = p3.x - p2.x;     s2_y = p3.y - p2.y;

		T s, t;
		s = (-s1_y * (p0.x - p2.x) + s1_x * (p0.y - p2.y)) / (-s2_x * s1_y + s1_x * s2_y);
		t = (s2_x * (p0.y - p2.y) - s2_y * (p0.x - p2.x)) / (-s2_x * s1_y + s1_x * s2_y);

		if(s >= 0 && s <= 1 && t >= 0 && t <= 1)
		{
			// Collision detected
			if(i != nullptr) {
				i->x = p0.x + (t * s1_x);
				i->y = p0.y + (t * s1_y);
			}
			return true;
		}

		return false; // No collision
	};
	template<typename T>
	static bool equal(const Vector2<T> &a, const Vector2<T> &b, float eps = 0.1f) {
		return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) <= eps * eps;
	};


	//SFML
	operator sf::Vector2<T>() const {
		return sf::Vector2<T>(x, y);
	};
	void draw(sf::RenderTarget& target, Vector2 p = Vector2::ZERO, sf::Color color = sf::Color::White) const {
		sf::Vertex ver[2] = {
			sf::Vertex(p, color),
			sf::Vertex(p + *this, color)
		};
		target.draw(ver, 2, sf::Lines);
	};
	void draw(sf::RenderTarget& target, Vector2 p, sf::Color colorA, sf::Color colorB) const {
		sf::Vertex ver[2] = {
			sf::Vertex(p, colorA),
			sf::Vertex(p + *this, colorB)
		};
		target.draw(ver, 2, sf::Lines);
	};
	void plot(sf::RenderTarget& target, float size = 2, sf::Color color = sf::Color::White) const {
		sf::CircleShape s(size / 2.f);
		s.setOrigin(size / 2.f, size / 2.f);
		s.setFillColor(color);
		s.setPosition(*this);
		target.draw(s);
	};
};

using Vector2f = Vector2<float>;
using Vector2d = Vector2<double>;

// Hash functions
namespace std {
	
	template <>
	struct hash<Vector2<float>> {
		size_t operator()(const Vector2<float>& k) const {
			return	(
							(std::hash<float>()(k.x)) ^
							(std::hash<float>()(k.y))
						);
		}
	};
	template <>
	struct hash<Vector2<double>> {
		size_t operator()(const Vector2<double>& k) const {
			return	(
							(std::hash<double>()(k.x)) ^
							(std::hash<double>()(k.y))
						);
		}
	};
	template <>
	struct hash<Vector2<int>> {
		size_t operator()(const Vector2<int>& k) const {
			return	(
				(std::hash<double>()(k.x)) ^
				(std::hash<double>()(k.y))
				);
		}
	};
};