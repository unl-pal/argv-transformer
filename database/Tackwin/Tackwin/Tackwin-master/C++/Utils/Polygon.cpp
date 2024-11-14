#include "Polygon.hpp"


Polygon::Polygon() {};
Polygon::Polygon(std::vector<Vector2<>> vertexes) : _vertexes(vertexes) {
	signedArea();
}

Polygon::~Polygon() {
}

float Polygon::signedArea() {
	if(_computedArea)
		return _A;
	_A = 0.f;
	_computedArea = true;
	for(size_t i = 0; i < _vertexes.size(); i++) {
		auto a = _vertexes[i];
		auto b = _vertexes[(i + 1) % _vertexes.size()];

		_A += a.x * b.y - b.x * a.y;
	}
	return _A /= 2.f;
}

float Polygon::signedArea() const {
	float A = 0.f;
	for(size_t i = 0; i < _vertexes.size(); i++) {
		auto a = _vertexes[i];
		auto b = _vertexes[(i + 1) % _vertexes.size()];

		A += a.x * b.y - b.x * a.y;
	}
	return A /= 2.f;
}

bool Polygon::contains(Vector2<> p) const {
	int n = 0;
	for(size_t i = 0; i < _vertexes.size(); i++) {
		Vector2<> a = _vertexes[i];
		Vector2<> b = _vertexes[(i + 1) % _vertexes.size()];

		if(Vector2<>::intersect(a, b, Vector2<>::ZERO, p))
			n++;
	}
	return n % 2 == 0;
}

void Polygon::draw(sf::RenderTarget& target, sf::Color outLineColor, sf::Color inColor) const {
	if(_vertexes.empty())
		return; //obviously...

	if(inColor != sf::Color::Transparent) {
		for(size_t i = 1; i < _vertexes.size() - 1; i++) {
			Vector2<> a = _vertexes[0];
			Vector2<> b = _vertexes[i];
			Vector2<> c = _vertexes[i + 1];

			sf::Vertex vert[3] = {
				sf::Vertex(a, inColor),
				sf::Vertex(b, inColor),
				sf::Vertex(c, inColor)
			};
			target.draw(vert, 3, sf::Triangles);
		}
	}
	for(size_t i = 0; i < _vertexes.size(); i++) {
		Vector2<> a = _vertexes[i];
		Vector2<> b = _vertexes[(i + 1) % _vertexes.size()];
		
		(b - a).draw(target, a, outLineColor, outLineColor);
	}
}

Vector2<> Polygon::centroid() const {
	float A = signedArea();
	float Cx = 0;
	float Cy = 0;

	for(size_t i = 0; i < size(); i++) {
		auto a = _vertexes[i];
		auto b = _vertexes[(i + 1) % size()];

		Cx += (a.x + b.x) * (a.x * b.y - b.x * a.y);
		Cy += (a.y + b.y) * (a.x * b.y - b.x * a.y);
	}

	return Vector2<>{ static_cast<float>(Cx), static_cast<float>(Cy) } / (6 * A);
}

std::vector<Vector2<>>& Polygon::getVertexes() {
	_computedArea = false;
	return _vertexes;
}

Vector2<>& Polygon::operator[](size_t i) {
	_computedArea = false;
	return _vertexes[i];
}
Vector2<> Polygon::operator[](size_t i) const {
	return _vertexes[i];
}
std::vector<Vector2<>>::const_iterator Polygon::find(const Vector2<>& v) const {
	return std::find(_vertexes.begin(), _vertexes.end(), v);
}
