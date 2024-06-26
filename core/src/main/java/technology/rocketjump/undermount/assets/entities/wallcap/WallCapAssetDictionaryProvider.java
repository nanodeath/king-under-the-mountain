package technology.rocketjump.undermount.assets.entities.wallcap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import technology.rocketjump.undermount.assets.TextureAtlasRepository;
import technology.rocketjump.undermount.assets.WallTypeDictionary;
import technology.rocketjump.undermount.assets.entities.model.SpriteDescriptor;
import technology.rocketjump.undermount.assets.entities.wallcap.model.WallCapAsset;
import technology.rocketjump.undermount.rendering.RenderMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static technology.rocketjump.undermount.assets.TextureAtlasRepository.TextureAtlasType.DIFFUSE_ENTITIES;
import static technology.rocketjump.undermount.assets.TextureAtlasRepository.TextureAtlasType.NORMAL_ENTITIES;
import static technology.rocketjump.undermount.assets.entities.creature.CreatureEntityAssetDictionaryProvider.addSprite;

@Singleton
public class WallCapAssetDictionaryProvider implements Provider<WallCapAssetDictionary> {

	private final TextureAtlasRepository textureAtlasRepository;
	private final WallTypeDictionary wallTypeDictionary;
	private WallCapAssetDictionary instance;

	@Inject
	public WallCapAssetDictionaryProvider(TextureAtlasRepository textureAtlasRepository, WallTypeDictionary wallTypeDictionary) {
		this.textureAtlasRepository = textureAtlasRepository;
		this.wallTypeDictionary = wallTypeDictionary;
	}

	@Override
	public WallCapAssetDictionary get() {
		if (instance == null) {
			instance = create();
		}
		return instance;
	}

	public WallCapAssetDictionary create() {
		TextureAtlas diffuseTextureAtlas = textureAtlasRepository.get(DIFFUSE_ENTITIES);
		TextureAtlas normalTextureAtlas = textureAtlasRepository.get(NORMAL_ENTITIES);
		FileHandle assetDefinitionsFile = Gdx.files.internal("assets/definitions/entityAssets/wallCapAssets.json");
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			List<WallCapAsset> assetList = objectMapper.readValue(assetDefinitionsFile.readString(),
					objectMapper.getTypeFactory().constructParametrizedType(ArrayList.class, List.class, WallCapAsset.class));

			for (WallCapAsset asset : assetList) {
				for (SpriteDescriptor spriteDescriptor : asset.getSpriteDescriptors().values()) {
					addSprite(spriteDescriptor, diffuseTextureAtlas, RenderMode.DIFFUSE);
					addSprite(spriteDescriptor, normalTextureAtlas, RenderMode.NORMALS);
				}
			}

			return new WallCapAssetDictionary(assetList, wallTypeDictionary);
		} catch (IOException e) {
			// TODO better exception handling
			throw new RuntimeException(e);
		}

	}

}
