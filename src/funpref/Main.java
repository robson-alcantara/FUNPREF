/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package funpref;

import funpref.controller.FUNPREFController;

/**
 *
 * @author Robson Santana
 */

public final class Main {

    public static void main(final String[] args) {        
        FUNPREFController funprefController = new FUNPREFController();
        funprefController.run();    
    }
}