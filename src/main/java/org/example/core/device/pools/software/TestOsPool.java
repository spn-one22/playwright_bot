package org.example.core.device.pools.software;

import org.example.core.device.constraints.presets.WindowsDesktopConstraint;
import org.example.core.device.pools.software.*;

import java.util.List;

public class TestOsPool {

    public static void main(String[] args) {

        // 1. constraint (Windows)
        WindowsDesktopConstraint constraint = new WindowsDesktopConstraint();

        // 2. pool
        OsPool pool = new OsPool();

        // 3. фильтрация
        List<OsOption> result = pool.filterByFamily(
                constraint.software().allowedOsFamilies()
        );

        // 4. вывод
        for (OsOption os : result) {
            System.out.println(os.name);
        }
    }
}