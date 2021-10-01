package net.javaguides.springboot.chart;

import java.util.List;

public interface DataService {

    List<Integer> getNumInfections();

    List<Integer> getNumReservations();

    List<Integer> getInfectionsPerWeek();

    List<List<Integer>> getReservationPerRoom();
}
