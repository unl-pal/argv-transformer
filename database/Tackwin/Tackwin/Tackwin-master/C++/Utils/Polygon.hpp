#pragma once
#include <SFML/Graphics.hpp>
#include <vector>
#include "Vector2.hpp"

///CLOSE NON SELF INTERACTING POLYGON
class Polygon {
private:
	bool _computedArea = false;
	float _A = 0.f;
	std::vector<Vector2<>> _vertexes;

public:
	Polygon();
	Polygon(std::vector<Vector2<>> vertexes);
	~Polygon();

	float signedArea();
	float signedArea() const;
	void draw(sf::RenderTarget& target, sf::Color outlineColor, sf::Color inColor = sf::Color::Transparent) const;
	bool contains(Vector2<> p) const;
	Vector2<> centroid() const;

	std::vector<Vector2<>>& getVertexes();

	size_t size() const{return _vertexes.size(); }

	std::vector<Vector2<>>::const_iterator begin() const { return _vertexes.begin(); } 
	std::vector<Vector2<>>::const_iterator end() const { return _vertexes.end(); }
	std::vector<Vector2<>>::const_iterator find(const Vector2<>& v) const;

	Vector2<>& operator[](size_t i);
	Vector2<> operator[](size_t i) const;
};

