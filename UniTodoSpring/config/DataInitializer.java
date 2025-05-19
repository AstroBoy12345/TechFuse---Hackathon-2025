package com.teamviewer.uni_todo.UniTodoSpring.config;

import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingArea;
import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingZone;
import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingSpot;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.ParkingAreaRepository;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.ParkingZoneRepository;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.ParkingSpotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ParkingAreaRepository parkingAreaRepository;
    private final ParkingZoneRepository parkingZoneRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    public DataInitializer(ParkingAreaRepository parkingAreaRepository,
                           ParkingZoneRepository parkingZoneRepository,
                           ParkingSpotRepository parkingSpotRepository) {
        this.parkingAreaRepository = parkingAreaRepository;
        this.parkingZoneRepository = parkingZoneRepository;
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Override
    public void run(String... args) {
        if (parkingAreaRepository.count() == 0) {
            System.out.println(">>> [DataInitializer] Δημιουργία προκαθορισμένων Parking Areas...");

            // ✅ Δημιουργία μιας κύριας περιοχής στάθμευσης (π.χ. "Ιωάννινα")
            ParkingArea area = new ParkingArea("Ιωάννινα", "Κεντρική περιοχή στάθμευσης στην πόλη των Ιωαννίνων");
            parkingAreaRepository.save(area);

            // ✅ Δημιουργία μίας Ζώνης (π.χ. "Ζώνη 1") στην περιοχή "Ιωάννινα"
            ParkingZone zone = new ParkingZone("Ζώνη 1", area);
            parkingZoneRepository.save(zone);

            // ✅ Σταθερές συντεταγμένες από το Frontend για τις Θέσεις Parking
            double[][] parkingSpotsData = {
                    {39.673864, 20.860371}, // Parking Spot 1
                    {39.673824, 20.860493}, // Parking Spot 2
                    {39.673732, 20.860650}, // Parking Spot 3
                    {39.673629, 20.860760}, // Parking Spot 4
                    {39.673314, 20.861054}, // Parking Spot 5
                    {39.673190, 20.861179}  // Parking Spot 6
            };

            // ✅ Δημιουργία θέσεων στάθμευσης με τις παραπάνω συντεταγμένες
            for (int i = 0; i < parkingSpotsData.length; i++) {
                double lat = parkingSpotsData[i][0];
                double lng = parkingSpotsData[i][1];

                ParkingSpot spot = new ParkingSpot(i + 1, lat, lng, true, zone);
                parkingSpotRepository.save(spot);
            }

            System.out.println(">>> [DataInitializer] Αρχικοποίηση ολοκληρώθηκε! Δημιουργήθηκαν:");
            System.out.println("    - 1 Parking Area (Ιωάννινα)");
            System.out.println("    - 1 Parking Zone (Ζώνη 1)");
            System.out.println("    - 6 Parking Spots με γεωγραφικές συντεταγμένες");
        } else {
            System.out.println(">>> [DataInitializer] Τα δεδομένα υπάρχουν ήδη. Δεν απαιτείται αρχικοποίηση.");
        }
    }
}
