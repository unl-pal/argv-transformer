#include "AssetsManager.hpp"
#include <Windows.h> //Yeah i know, this isn't portable, but whatever, the colors are worth it :-p
#include <cassert>

std::map<std::string, sf::SoundBuffer> AssetsManager::_sounds;
std::map<std::string, sf::Texture> AssetsManager::_textures;
std::map<std::string, sf::Music> AssetsManager::_musics;
std::map<std::string, sf::Image> AssetsManager::_images;
std::map<std::string, sf::Font> AssetsManager::_fonts;

AssetsManager::AssetsManager() {
}
AssetsManager::~AssetsManager() {
}

bool AssetsManager::loadTexture(const std::string &key, const std::string &path) {
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_BLUE);
	std::printf("Loading: ");
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	std::printf("%s\n", path.c_str());
	auto &ref = _textures[key];

	bool loaded = ref.loadFromFile(path);
	if(!loaded) {
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED);
		printf(" Couldn't load file /!\\\n");
	}
	else {
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_GREEN);
		printf("Succes !\n");
	}
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	return loaded;
}

const sf::Texture& AssetsManager::getTexture(const std::string &key) {
	auto it = _textures.find(key);
	assert(it != std::end(_textures) && "Texture don't exist");
	return it->second;
}

bool AssetsManager::loadImage(const std::string &key, const std::string &path) {
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_BLUE);
	std::printf("Loading: ");
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	std::printf("%s\n", path.c_str());
	auto &ref = _images[key];

	bool loaded = ref.loadFromFile(path);
	if(!loaded) {
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED);
		printf(" Couldn't load file /!\\\n");
	} else {
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_GREEN);
		printf("Succes !\n");
	}
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	return loaded;
}

const sf::Image& AssetsManager::getImage(const std::string &key) {
	auto it = _images.find(key);
	assert(it != std::end(_images) && "Image don't exist");
	return it->second;
}

bool AssetsManager::loadFont(const std::string &key, const std::string &path) {
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_BLUE);
	std::printf("Loading: ");
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	std::printf("%s\n", path.c_str());
	auto &ref = _fonts[key];

	bool loaded = ref.loadFromFile(path);
	if(!loaded) {
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED);
		printf(" Couldn't load file /!\\\n");
	} else {
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_GREEN);
		printf("Succes !\n");
	}
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	return loaded;
}

const sf::Font& AssetsManager::getFont(const std::string &key) {
	auto it = _fonts.find(key);
	assert(it != std::end(_fonts) && "Font don't exist");
	return it->second;
}

bool AssetsManager::loadSound(const std::string &key, const std::string &path) {
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_BLUE);
	std::printf("Loading: ");
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	std::printf("%s\n", path.c_str());
	auto &ref = _sounds[key];

	bool loaded = ref.loadFromFile(path);
	if(!loaded) {
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED);
		printf(" Couldn't load file /!\\\n");
	} else {
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_GREEN);
		printf("Succes !\n");
	}
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	return loaded;
}

const sf::SoundBuffer& AssetsManager::getSound(const std::string &key) {
	auto it = _sounds.find(key);
	assert(it != std::end(_sounds) && "Sound don't exist");
	return it->second;
}


bool AssetsManager::openMusic(const std::string &key, const std::string &path) {
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_BLUE);
	std::printf("Loading: ");
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	std::printf("%s\n", path.c_str());
	auto &ref = _musics[key];

	bool loaded = ref.openFromFile(path);
	if(!loaded) {
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED);
		printf(" Couldn't load file /!\\\n");
	} else {
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_GREEN);
		printf("Succes !\n");
	}
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	return loaded;
}

sf::Music& AssetsManager::getMusic(const std::string &key) {
	auto it = _musics.find(key);
	assert(it != std::end(_musics) && "Music don't exist");
	return it->second;
}


