package attackPhrase;

import collision.CollisionChecker;
import entity.Player;
import main.AssetLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;



public class Battle {
    private Pokemon player;
    private Pokemon enemy;
    private JTextArea battleLog;
    private JPanel movePanel;
    private JPanel battleScenePanel;
    private JButton[] moveButtons;
    private static final double CRITICAL_HIT_MULTIPLIER = 1.5;
    private static final double CRITICAL_HIT_CHANCE = 0.0625; // 6.25% chance

    private PokemonPanel playerPanel;
    private PokemonPanel enemyPanel;

    public Battle(Pokemon player, Pokemon enemy) {
        this.player = player;
        this.enemy = enemy;
        this.battleLog = new JTextArea();
        this.movePanel = new JPanel();
        this.moveButtons = new JButton[4];
        this.battleScenePanel = new JPanel();
        setupUI();
    }

    private void setupUI() {
        JFrame frame = new JFrame("Battle");
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        battleLog.setEditable(false);
        battleLog.setLineWrap(true);
        battleLog.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(battleLog);
        scrollPane.setPreferredSize(new Dimension(300, 600));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane, BorderLayout.WEST);

        movePanel.setLayout(new GridLayout(1, 4));
        for (int i = 0; i < 4; i++) {
            moveButtons[i] = new JButton();
            moveButtons[i].setEnabled(false);
            moveButtons[i].addActionListener(new MoveButtonListener(i));
            movePanel.add(moveButtons[i]);
        }

        battleScenePanel.setLayout(null);

        // player panel
        playerPanel = new PokemonPanel(player.getImagePath());
        playerPanel.setBounds(100, 300, 100, 100);
        battleScenePanel.add(playerPanel);

        JLabel playerNameLabel = new JLabel(player.getName() + " Lv" + player.getlvl());
        playerNameLabel.setBounds(100, 400, 200, 20);
        battleScenePanel.add(playerNameLabel);

        // enemy panel
        enemyPanel = new PokemonPanel(enemy.getImagePath());
        enemyPanel.setBounds(500, 100, 100, 100);
        battleScenePanel.add(enemyPanel);

        JLabel enemyNameLabel = new JLabel(enemy.getName() + " Lv" + enemy.getlvl());
        enemyNameLabel.setBounds(500, 200, 200, 20);
        battleScenePanel.add(enemyNameLabel);

        frame.add(battleScenePanel, BorderLayout.CENTER);
        frame.add(movePanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        startBattle();
    }


    private void startBattle() {
        for (int i = 0; i < player.getMoveSet().getMoves().size(); i++) {
            moveButtons[i].setText(player.getMove(i).getName());
            moveButtons[i].setEnabled(true);
        }
        updateBattleLog();
        battleScenePanel.repaint();
    }


    private void executeMove(Pokemon attacker, Pokemon defender, Move move) {
        boolean isCriticalHit = new Random().nextDouble() < CRITICAL_HIT_CHANCE;
        int damage = calculateDamage(attacker, defender, move, isCriticalHit);
        defender.takeDamage(damage);


        double effectiveness = TypeEffectiveness.getEffectiveness(move.getType(), defender.getType());
        String effectivenessText = "";
        if (effectiveness > 1.0) {
            effectivenessText = " It's super effective!";
        } else if (effectiveness < 1.0) {
            effectivenessText = " It's not very effective...";
        }


        battleLog.append(String.format("%s used %s and dealt %d damage to %s.%s%s\n",
                attacker.getName(),
                move.getName(),
                damage,
                defender.getName(),
                isCriticalHit ? " It's a critical hit!" : "",
                effectivenessText));
        updateBattleLog();
        battleScenePanel.repaint();
    }


    private int calculateDamage(Pokemon attacker, Pokemon defender, Move move, boolean isCriticalHit) {
        int level = attacker.getlvl();
        double attackStat = attacker.getAttack();
        double defenseStat = defender.getDefense();
        double criticalMultiplier = isCriticalHit ? CRITICAL_HIT_MULTIPLIER : 1.0;
        double effectiveness = TypeEffectiveness.getEffectiveness(move.getType(), defender.getType());
        return (int)(((((2 * level / 5.0 + 2) * move.getPower() * attackStat / defenseStat) / 50) + 2) * criticalMultiplier * effectiveness);
    }


    private void updateBattleLog() {
        battleLog.append(String.format("%s HP: %.0f\n%s HP: %.0f\n", player.getName(), player.getHealth(), enemy.getName(), enemy.getHealth()));
        if (player.getHealth() <= 0 || enemy.getHealth() <= 0) {
            for (JButton button : moveButtons) {
                button.setEnabled(false);
            }
            if (player.getHealth() > 0) {
                battleLog.append(player.getName() + " wins!\n");
                enemyPanel.setVisible(false);
                CollisionChecker.battleOpen = false;
            } else {
                battleLog.append(enemy.getName() + " wins!\n");
                playerPanel.setVisible(false);
                CollisionChecker.battleOpen = false;
            }
            Timer timer = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(battleScenePanel);
                    frame.dispose();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }



    private class MoveButtonListener implements ActionListener {
        private int moveIndex;


        public MoveButtonListener(int moveIndex) {
            this.moveIndex = moveIndex;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            Move playerMove = player.getMove(moveIndex);
            Move enemyMove = enemy.getMove(new Random().nextInt(enemy.getMoveSet().getMoves().size()));


            if (player.getSpeed() >= enemy.getSpeed()) {
                executeMove(player, enemy, playerMove);
                if (enemy.getHealth() > 0) {
                    executeMove(enemy, player, enemyMove);
                }
            } else {
                executeMove(enemy, player, enemyMove);
                if (player.getHealth() > 0) {
                    executeMove(player, enemy, playerMove);
                }
            }
            battleScenePanel.repaint();
        }
    }
    //YOU CAN CHANGE THIS, THIS IS FOR PLACEHOLDER the paint part
    private class PokemonPanel extends JPanel {
        private Image image;


        public PokemonPanel(String imagePath) {
            this.image = new ImageIcon(imagePath).getImage();
            setOpaque(false);
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                int width = getWidth();
                int height = getHeight();
                g.drawImage(image, 0, 0, width, height, this);
            } else {
                g.setColor(Color.RED);
                g.drawString("Image not found", 10, 20);
            }
        }
    }


    public static void battleWindow() {

        // Create an array to hold the three Pokemon players
        Pokemon[] players = new Pokemon[3];

        // Initialize the three Pokemon players with their respective attributes
        players[0] = new Pokemon("Charmander", 50, 65, 60, 52, 39, 43, 100, "Fire", AssetLoader.getFile("Player/Charmander_up2_16x16.png").getPath());
        players[1] = new Pokemon("Bulbasaur",1, 10, 1, 20, 100, 5, 100, "Grass", AssetLoader.getFile("Player/Bulbasaur_up2_16x16.png").getPath());
        players[2] = new Pokemon("Squirtle",64, 43, 50, 48, 44, 65, 100, "Water", AssetLoader.getFile("Player/Squirtle_up1_16x16.png").getPath());

        // Add moves for each player
        players[0].addMove(new Move("Slash", 70, "Normal", 1.0));
        players[0].addMove(new Move("Ember", 40, "Fire", 1.0));
        players[0].addMove(new Move("Dragon Breath", 65, "Dragon", 1.0));
        players[0].addMove(new Move("Metal Claw", 50, "Steel", 0.95));

        players[1].addMove(new Move("Take Down", 80, "Normal", 0.85));
        players[1].addMove(new Move("Vine Whip", 45, "Grass", 1.0));
        players[1].addMove(new Move("Acid Spray", 40, "Poison", 1.0));
        players[1].addMove(new Move("Razor Leaf", 55, "Grass", 0.95));

        players[2].addMove(new Move("Rapin Spin", 50, "Normal", 1.0));
        players[2].addMove(new Move("Water Gun", 40, "Water", 1.0));
        players[2].addMove(new Move("Zen Headbutt", 80, "Psychic", 0.90));
        players[2].addMove(new Move("Mud Shot", 55, "Ground", 0.95));


        Pokemon[] enemies = {
                new Pokemon("Bulbasaur", 1, 49, 49, 45, 65, 45, 100, "Grass", AssetLoader.getFile("Player/Bulbasaur_down2_16x16.png").getPath()),
                new Pokemon("Squirtle", 1, 48, 65, 44, 50, 43, 100, "Water", AssetLoader.getFile("Player/Squirtle_down1_16x16.png").getPath()),
                new Pokemon("Pidgey", 1, 45, 40, 35, 56, 35, 100, "Flying", AssetLoader.getFile("NPC/Pidgey_left.png").getPath())
        };

        // randomly select enemy pokemon
        Pokemon enemy = enemies[new Random().nextInt(enemies.length)];
        enemy.addMove(new Move("Tackle", 40, "Normal", 1.0));
        enemy.addMove(new Move("Quick Attack", 40, "Normal", 1.0));


        if(Player.selectedPokemon.equals("Bulbasaur")){
            new Battle(players[1], enemy);
        }
        if(Player.selectedPokemon.equals("Squirtle")){
            new Battle(players[2], enemy);
        }
        if(Player.selectedPokemon.equals("Charmander")){
            new Battle(players[0], enemy);
        }

    }
}
