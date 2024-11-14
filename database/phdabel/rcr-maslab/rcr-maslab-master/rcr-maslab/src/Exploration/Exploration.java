package Exploration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.EntityID;

/*Exploração armazena o mapeamento conhecido em um Hash da forma:
 *  	- StandardEntity ID : {String Problem, int TimeStep, Tempo de Vida Restante do Civil, Gravidade do Incendio}
 * O problema é definido da seguinte forma: abc { a- civis, b- fogo, c- bloqueio}, onde:
 * 		- 0 : ausencia de problema
 * 		- 1 : problema conhecido
 * Por exemplo:
 * 		- 001 : bloqueio
 * 		- 011 : fogo e bloqueio
 *      - 000 : nada
 * */
public class Exploration {

	@SuppressWarnings("unused")
	private StandardWorldModel model;
	static int TamanhoLista = 10;
	@SuppressWarnings("rawtypes")
	public HashMap<StandardEntity, List> Exploracao = new HashMap<StandardEntity, List>();

}
