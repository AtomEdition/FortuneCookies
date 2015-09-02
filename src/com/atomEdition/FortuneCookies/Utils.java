package com.atomEdition.FortuneCookies;

import com.atomEdition.FortuneCookies.model.Position;
import com.atomEdition.FortuneCookies.model.Prophecy;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: FruityDevil
 * Date: 03.12.14
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public abstract class Utils {
    public static final Double SHAKE_THRESHOLD_DEFAULT = 1.55d;
    public static final Integer SHAKE_COUNT = 2;
    public static final Integer SHAKE_TIME = 1000;
    public static final LinkedList<Double> CALIBRATION = new LinkedList<Double>();
    public static final String PREFERENCES_NAME = "preferences";
    public static final String PREFERENCES_LIST_SIZE = "size";
    public static final String PREFERENCES_LIST_ELEMENT = "prophecy_";
    public static final String PREFERENCES_SHAKE_THRESHOLD = "calibration_";
    public static final Integer VIBRATE_FALL_TIME = 100;
    public static final Integer VIBRATE_CRACK_TIME = 60;
    public static final Integer PROPHECY_CATEGORIES_COUNT_TOTAL = 6;
    //todo: count total prophecy count in service
    public static final Integer PROPHECY_COUNT_TOTAL = 165;
    public static final Integer[] PROPHECY_ARRAY_WAS_ADDED_IN_LAST_PATCH = {55, 3, 6, 12, 14, 20};
    public static final String PROPHECY_CATEGORY_NAME = "prophecies_";
    public static final String ABOUT_TEXT_VIEW_IDENTIFIER = "about_text_";
    public static final String ABOUT_TEXT_STRING_IDENTIFIER = "about_text_";
    public static final Integer PROPHECY_WIDTH = 400;
    public static final Integer ANIMATION_APPEAR_DURATION = 200;
    public static final Integer ANIMATION_MOVE_TO_CENTER_DURATION = 1000;
    public static final Integer ANIMATION_MOVE_OUT_DURATION = 800;
    public static final Integer ANIMATION_MOVE_OUT_SIDES_DURATION = 500;
    public static final Integer ANIMATION_PROPHECY_DURATION = 400;
    public static final Integer ANIMATION_CRUMBS_DURATION = 150;
    public static final Long COOLDOWN = (1000L * 60 * 60) * 4;
    public static final Integer COOKIES_COUNT = 5;
    public static final String COOKIE_NAME = "cookie_";
    public static final String COOKIE_HALF_LEFT_NAME = "cookie_half_left_";
    public static final String COOKIE_HALF_RIGHT_NAME = "cookie_half_right_";
    public static final String PROPHECY_NAME = "paper_";
    public static final String CRUMBS_NAME = "crumbs_";
    public static final Integer COOKIE_SIZE = 100;
    public static final Integer BORDER_SIZE = 25*2;
    private static final Integer DISTANCE_BETWEEN_COOKIES = 85;
    public static Double SHAKE_THRESHOLD = 1.5d;
    public static Integer SHAKE_COUNT_CURRENT = 0;
    public static Integer SCREEN_HEIGHT;
    public static Integer SCREEN_WIDTH;
    public static Integer SCREEN_LIMIT;
    public static LinkedList<Integer> COOKIES = new LinkedList<Integer>();
    public static LinkedList<Prophecy> PROPHECIES = new LinkedList<Prophecy>();
    public static boolean IS_TUTORIAL_NEEDED = false;
    public static Random random = new Random();
    private static LinkedList<Position> POSITIONS = new LinkedList<Position>();

    public static void clearPositions(){
        POSITIONS.clear();
    }

    public static void clearCookies(){
        COOKIES.clear();
    }

    private static boolean isPositionCorrect(int oldPosition, int newPosition){
        oldPosition+= (COOKIE_SIZE/2);
        newPosition+= (COOKIE_SIZE/2);
        if(Math.abs(newPosition - oldPosition) < DISTANCE_BETWEEN_COOKIES)
            return false;
        return true;
    }

    public static Position placeCookies(){
        Position newPosition = new Position(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT));
        if(POSITIONS.size()>0)
            for(Position position : POSITIONS)
                if(!isPositionCorrect(position.getX(), newPosition.getX()) &&
                        !isPositionCorrect(position.getY(),newPosition.getY()))
                    return placeCookies();
        POSITIONS.add(newPosition);
        return POSITIONS.getLast();
    }

    public static boolean isNear(float value1, float value2, int radius){
        return !(Math.abs(value1 - value2) > radius);
    }

    public static void generateCookies(){
        COOKIES.clear();
        for(int i=0; i<COOKIES_COUNT; i++)
            COOKIES.add(random.nextInt(PROPHECY_CATEGORIES_COUNT_TOTAL));
    }

    public static Double average(LinkedList<Double> linkedList){
        for(Double value : linkedList)
            SHAKE_THRESHOLD += value;
        SHAKE_THRESHOLD /= linkedList.size();
        return SHAKE_THRESHOLD;
    }

    public static LinkedList<Prophecy> descendingList(LinkedList<Prophecy> inputList){
        LinkedList<Prophecy> newList = new LinkedList<Prophecy>();
        while (inputList.size() > 0){
            newList.add(inputList.getLast());
            inputList.removeLast();
        }
        return newList;
    }
}
