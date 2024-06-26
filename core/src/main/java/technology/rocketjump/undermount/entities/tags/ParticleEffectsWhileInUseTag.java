package technology.rocketjump.undermount.entities.tags;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import org.pmw.tinylog.Logger;
import technology.rocketjump.undermount.entities.components.furniture.FurnitureParticleEffectsComponent;
import technology.rocketjump.undermount.entities.model.Entity;
import technology.rocketjump.undermount.gamecontext.GameContext;

import static technology.rocketjump.undermount.entities.model.EntityType.FURNITURE;

public class ParticleEffectsWhileInUseTag extends Tag {

	@Override
	public String getTagName() {
		return "PARTICLE_EFFECTS_WHILE_IN_USE";
	}

	@Override
	public boolean isValid(TagProcessingUtils tagProcessingUtils) {
		for (String arg : getArgs()) {
			if (tagProcessingUtils.particleEffectTypeDictionary.getByName(arg) == null) {
				Logger.error("Can not find particle effect with name " + arg + " for " + this.getClass().getSimpleName());
				return false;
			}
		}
		return true;
	}

	@Override
	public void apply(Entity entity, TagProcessingUtils tagProcessingUtils, MessageDispatcher messageDispatcher, GameContext gameContext) {
		if (entity.getType().equals(FURNITURE)) {
			FurnitureParticleEffectsComponent particleEffectsComponent = entity.getOrCreateComponent(FurnitureParticleEffectsComponent.class);
			particleEffectsComponent.init(entity, messageDispatcher, gameContext);
			for (String arg : getArgs()) {
				particleEffectsComponent.getParticleEffectsWhenInUse().add(tagProcessingUtils.particleEffectTypeDictionary.getByName(arg));
			}
		} else {
			Logger.error(this.getClass().getSimpleName() + " must apply to a furniture entity");
		}
	}

}
