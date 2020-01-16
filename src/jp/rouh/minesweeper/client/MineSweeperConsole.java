package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.*;
import jp.rouh.minesweeper.field.MineCellView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.BiConsumer;

public class MineSweeperConsole implements MineSweeperObserver{
    private MineSweeper game;
    private boolean finished = false;
    private MineSweeperConsole(MineSweeper game){
        this.game = game;
        this.game.setObserver(this);
        startPlaying();
    }
    public void startPlaying(){
        try(var br = new BufferedReader(new InputStreamReader(System.in))){
            playing:
            while(true){
                show();
                System.out.print("command: ");
                String[] commandLine = br.readLine()
                        .trim().split("\\s");
                if(commandLine.length==0){
                    System.out.println("invalid(empty)");
                    continue;
                }
                String command = commandLine[0].toLowerCase();
                BiConsumer<Integer, Integer> action;
                if(command.equals("open")){
                    action = game::open;
                }else if(command.equals("flag")){
                    action = game::toggleFlag;
                }else{
                    System.out.println("invalid(unknown command)");
                    continue;
                }
                if(commandLine.length%2==0){
                    System.out.println("invalid(number of argument)");
                    continue;
                }
                int argumentCount = (commandLine.length - 1)/2;
                for(int i = 0; i<argumentCount; i++){
                    int x = Integer.parseInt(commandLine[2*i + 1]);
                    int y = Integer.parseInt(commandLine[2*i + 2]);
                    try{
                        action.accept(x, y);
                    }catch(NumberFormatException e){
                        System.out.println("invalid(not number): "+x+" "+y);
                    }catch(IllegalArgumentException e){
                        System.out.println("invalid(out of range): ("+x+","+y+")");
                    }
                    System.out.println();
                    if(finished){
                        break playing;
                    }
                }
            }
            show();
            if(game.isSecured()){
                System.out.println("you win");
            }else if(game.isExploded()){
                System.out.println("you lose");
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    private void show(){
        StringBuilder line = new StringBuilder();
        line.append("   ");
        for(int x = 0; x<game.getWidth(); x++){
            line.append("|");
            line.append(x/10==0?" ":x/10<10?Integer.toString(x/10):"E");
        }
        line.append("|");
        System.out.println(line);
        line.setLength(0);
        line.append("   ");
        for(int x = 0; x<game.getWidth(); x++){
            line.append("|");
            line.append(x%10);
        }
        line.append("|");
        System.out.println(line);
        for(int y = 0; y<game.getHeight(); y++){
            line.setLength(0);
            line.append(String.format("|%2s", y));
            for(int x = 0; x<game.getWidth(); x++){
                line.append("|");
                line.append(textOf(game.getView(x, y)));
            }
            line.append("|");
            System.out.println(line.toString());
        }
        line.setLength(0);
        line.append("|MINES=");
        line.append(String.format("%3s", Math.min(game.getEstimatedRemainingMineCount(), 999)));
        line.append("|COUNT=");
        line.append(String.format("%4s", Math.min(game.getTimeCount(), 9999)));
        line.append("|");
        System.out.println(line);
    }
    @SuppressWarnings("fallthrough")
    private static String textOf(MineCellView view){
        switch(view){
            case EXPOSED_0: return " ";
            case EXPOSED_1: return "1";
            case EXPOSED_2: return "2";
            case EXPOSED_3: return "3";
            case EXPOSED_4: return "4";
            case EXPOSED_5: return "5";
            case EXPOSED_6: return "6";
            case EXPOSED_7: return "7";
            case EXPOSED_8: return "8";
            case EXPOSED_MINE: return "X";
            case COVERED: return "■";
            case FLAGGED: //fallthrough
            case RESULT_COVERED_MINE: return "▼";
            case RESULT_FLAGGED_SAFE: return "▽";
            default: return null;
        }
    }
    @Override
    public void updateRemainingMineCount(){
        //ignored
    }
    @Override
    public void updateTimeCount(){
        //ignored
    }
    @Override
    public void updateStatus(){
        finished = game.isFinished();
    }
    @Override
    public void updateCell(int x, int y){
        //ignored
    }
    public static void main(String[] args){
        new MineSweeperConsole(new SafeLaunchMineSweeper(BasicDifficulty.BEGINNER));
    }
}
