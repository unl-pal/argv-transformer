package util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import maps.convert.legacy2gml.RoadInfo;

import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.Road;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.EntityID;

import util.Setores;

public final class MASLABRouting {

	/*
	 * private Map<EntityID, Set<EntityID>> Setor1; private Map<EntityID,
	 * Set<EntityID>> Setor2; private Map<EntityID, Set<EntityID>> Setor3;
	 * private Map<EntityID, Set<EntityID>> Setor4; private Map<EntityID,
	 * Set<EntityID>> Principais;
	 */
	private Map<EntityID, List<EntityID>> PontosPrincipais;
	private List<EntityID> refugeIDs;
	private List<EntityID> waterIDs;
	private List<EntityID> buildingIDs;
	private List<EntityID> principalIDs;
	private List<EntityID> setor1IDs;
	private List<EntityID> setor2IDs;
	private List<EntityID> setor3IDs;
	private List<EntityID> setor4IDs;
	private List<EntityID> globalIDs;
	private MASLABBFSearch GlobalSearch;
	private MASLABBFSearch S1search;
	private MASLABBFSearch S2search;
	private MASLABBFSearch S3search;
	private MASLABBFSearch S4search;
	private MASLABBFSearch Psearch;
	private StandardWorldModel model;
	private Random r = new Random();
	private MASLABSectoring sectoring;

	/**
	 * Identifica os tipos das vias: Principal: Vias que delimitam os setores
	 * (Norte-Sul e Leste-Oeste); Secundario: Vias que ligam edifícios
	 * importantes e a via principal; Outros: Demais vias dos setores;
	 */
	public enum Tipos {
		Principal, Secundario, Outros;
	}
}
