package technology.rocketjump.undermount.entities.factories.names;

import com.badlogic.gdx.math.RandomXS128;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;
import technology.rocketjump.undermount.entities.factories.DwarvenNameGenerator;
import technology.rocketjump.undermount.entities.model.physical.creature.HumanoidName;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.fest.assertions.Assertions.assertThat;
import static technology.rocketjump.undermount.entities.model.physical.creature.Gender.FEMALE;
import static technology.rocketjump.undermount.entities.model.physical.creature.Gender.MALE;

public class DwarvenNameGeneratorTest {

	private DwarvenNameGenerator nameGenerator;

	@Before
	public void setup() {
		nameGenerator = Guice.createInjector().getInstance(DwarvenNameGenerator.class);
	}

	@Test
	public void test_createsNonEmptyName() {
		HumanoidName name = nameGenerator.create(1L, MALE);

		assertThat(name.getFirstName()).isNotEmpty();
		assertThat(name.getLastName()).isNotEmpty();
	}

	@Test
	public void bigNamePrintoutTest() {
		Random random = new RandomXS128();
		for (int i = 0; i < 100; i++) {
			System.out.println(nameGenerator.create(random.nextLong(), random.nextBoolean() ? MALE : FEMALE));
		}
	}

	@Test
	public void alliterationTest() {
		List<HumanoidName> allNames = new LinkedList<>();
		int alliterative = 0;

		Random random = new RandomXS128();
		int total = 10000;
		for (int i = 0; i < total; i++) {
			HumanoidName name = nameGenerator.create(random.nextLong(), random.nextBoolean() ? MALE : FEMALE);

			String firstLetter = name.getFirstName().substring(0, 1);
			if (name.getLastName().startsWith(firstLetter)) {
				alliterative++;
			}
		}

		float percentage = Math.round((float)alliterative) / ((float)total) * 100;

		System.out.println(alliterative + " of " + total + " are alliterative - " + percentage + "%");
	}
}