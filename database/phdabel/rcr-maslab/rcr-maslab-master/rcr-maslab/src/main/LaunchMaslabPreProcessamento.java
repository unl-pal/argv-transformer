package main;

import agent.MASLABFireBrigade;
import agent.MASLABPoliceForce;
import agent.MASLABAmbulanceTeam;
import agent.MASLABCentre;
import agent.MASLABDummyAgent;

import java.io.IOException;

import rescuecore2.components.ComponentLauncher;
import rescuecore2.components.TCPComponentLauncher;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.connection.ConnectionException;
import rescuecore2.registry.Registry;
import rescuecore2.misc.CommandLineOptions;
import rescuecore2.config.Config;
import rescuecore2.config.ConfigException;
import rescuecore2.Constants;
import rescuecore2.log.Logger;

import rescuecore2.standard.entities.StandardEntityFactory;
import rescuecore2.standard.entities.StandardPropertyFactory;
import rescuecore2.standard.messages.StandardMessageFactory;

/**
 * Launcher for training agents. Lança no simulador o agente que gera o arquivo de pré processamento.
 * Para executar basta iniciar o simulador e em seguida executar esta classe. 
 * Não precisar iniciar a simulação, basta que o agente seja lançado no simulador para gerar o arquivo de treinamento.
 */
public final class LaunchMaslabPreProcessamento {

    private static final String FIRE_BRIGADE_FLAG = "-fb";
    private static final String POLICE_FORCE_FLAG = "-pf";
    private static final String AMBULANCE_TEAM_FLAG = "-at";
    private static final String CIVILIAN_FLAG = "-cv";
}
