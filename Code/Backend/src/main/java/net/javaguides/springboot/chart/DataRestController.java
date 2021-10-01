package net.javaguides.springboot.chart;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataRestController {

    List<Integer> lista;

    @Autowired
    private DataServiceImpl dataService;

    
    /** 
     * Get the a JSONArray with the infection number
     * @return JSONArray
     */
    @GetMapping(value = { "/admin/charts/getInfections" })
    public JSONArray getInfections() {
        List<Integer> lista = dataService.getNumInfections();
        JSONArray jArray = new JSONArray();
        for (Integer a : lista) {
            JSONObject obj = new JSONObject();
            obj.put("numInfections", a);
            jArray.add(obj);
        }
        return jArray;
    }

    
    /** 
     * Get a JSONArray with the reservations
     * @return JSONArray
     */
    @GetMapping(value = { "/admin/charts/reservation" })
    public JSONArray getReservations() {
        List<Integer> lista = dataService.getNumReservations();
        JSONArray jArray = new JSONArray();
        for (Integer a : lista) {
            JSONObject obj = new JSONObject();
            obj.put("numReservations", a);
            jArray.add(obj);
        }
        return jArray;
    }

    
    /** 
     * Get a JSONArray with the infecctions per week
     * @return JSONArray
     */
    @GetMapping(value = { "/admin/charts/infectionPerWeek" })
    public JSONArray getInfectionsPerWeek() {
        List<Integer> lista = dataService.getInfectionsPerWeek();
        JSONArray jArray = new JSONArray();
        for (Integer a : lista) {
            JSONObject obj = new JSONObject();
            obj.put("infectionsPerWeek", a);
            jArray.add(obj);
        }
        return jArray;
    }

    
    /** 
     * Get a JSONArray with the number of reservations per room
     * @return JSONArray
     */
    @GetMapping(value = { "/admin/charts/reservationPerRoom" })
    public JSONArray getREservationPerRoom() {
        List<List<Integer>> lista = dataService.getReservationPerRoom();
        JSONArray jArray = new JSONArray();
        for (List<Integer> a : lista) {
            JSONObject obj = new JSONObject();
            obj.put("reservationPerRoom", a);
            jArray.add(obj);
        }
        return jArray;
    }

}
