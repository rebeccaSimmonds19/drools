package org.kie.kproject;

import org.junit.Ignore;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.sample.model.Fire;
import org.kie.sample.model.Room;
import org.kie.sample.model.Sprinkler;

import static org.junit.Assert.assertEquals;

public class KProjectTest {

    @Test
    public void testKJar() throws Exception {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("FireAlarmKBase.session");

        Room room = new Room("101");
        kSession.insert(room);
        Sprinkler sprinkler = new Sprinkler(room);
        kSession.insert(sprinkler);
        Fire fire = new Fire(room);
        FactHandle fireFH = kSession.insert(fire);

        int rules = kSession.fireAllRules();
        assertEquals(2, rules);

        kSession.delete(fireFH);
        rules = kSession.fireAllRules();
        assertEquals(3, rules);
    }
}