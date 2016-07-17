package com.minemeander;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import com.minemeander.objects.Climber;
import com.minemeander.objects.Collectable;
import com.minemeander.objects.CollisionCategory;
import com.minemeander.objects.GameObject;
import com.minemeander.objects.GameObjectData;
import com.minemeander.objects.Avatar;
import com.minemeander.objects.LevelObjectManager;
import com.minemeander.objects.Avatar.AvatarStateEnum;

public class CollisionManager implements ContactFilter, ContactListener{

	public LevelObjectManager objectManager;

	public CollisionManager(LevelObjectManager objectManager) {
		this.objectManager = objectManager;
	}
	
	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		final Body bodyA = fixtureA.getBody();
		final Body bodyB = fixtureB.getBody();
		GameObjectData userDataA = (GameObjectData) bodyA.getUserData();
		GameObjectData userDataB = (GameObjectData) bodyB.getUserData();
		CollisionCategory categoryA = userDataA.collisionCategory;
		CollisionCategory categoryB = userDataB.collisionCategory;		
		Avatar avatar = objectManager.getAvatar();
		
		if (categoryA == CollisionCategory.AVATAR || categoryB == CollisionCategory.AVATAR) {
			if (avatar.isDead()) {
				return false;
			}
		}
		
		// Disable avatar-platform collision when climbing
		if ((categoryA == CollisionCategory.AVATAR && categoryB == CollisionCategory.TRAVERSABLE_PLATFORM)
					|| (categoryB == CollisionCategory.AVATAR && categoryA == CollisionCategory.TRAVERSABLE_PLATFORM)) {
			return !avatar.isClimbing() || !avatar.wasDraggingDown;
		}
		// Disable enemy-platform collision when climbing
		else if (categoryA == CollisionCategory.ENEMY && categoryB == CollisionCategory.TRAVERSABLE_PLATFORM) {
			GameObject gameObject = objectManager.get(userDataA.id);
			if (gameObject instanceof Climber) {
				return !((Climber)gameObject).isClimbing();
			}
			return true;
		}
		else if (categoryB == CollisionCategory.ENEMY && categoryA == CollisionCategory.TRAVERSABLE_PLATFORM) {			
			GameObject gameObject = objectManager.get(userDataB.id);
			if (gameObject instanceof Climber) {
				return !((Climber)gameObject).isClimbing();
			}
			return true;			
		}
				
		if (categoryA == CollisionCategory.ENEMY && categoryB == CollisionCategory.COLLECTABLE) {			
			return false;
		}
		else if (categoryB == CollisionCategory.ENEMY && categoryA == CollisionCategory.COLLECTABLE) {			
			return false;			
		}
		// Disable enemy-enemy collision (always)
		else if (categoryA == CollisionCategory.ENEMY && categoryB == CollisionCategory.ENEMY) {
			return false;
		}

		if (categoryA == CollisionCategory.ENEMY && categoryB == CollisionCategory.AVATAR) {
			return !objectManager.getAvatar().isInvicible();
		}
		else if (categoryB == CollisionCategory.ENEMY && categoryA == CollisionCategory.AVATAR) {
			return !objectManager.getAvatar().isInvicible();
		}
		
		return true;
	}
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Avatar avatar = objectManager.getAvatar();
		
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		Body bodyA = fixtureA.getBody();
		Body bodyB = fixtureB.getBody();
		GameObjectData userDataA = (GameObjectData) bodyA.getUserData();		
		GameObjectData userDataB = (GameObjectData) bodyB.getUserData();
	
		if (userDataA.collisionCategory == CollisionCategory.AVATAR || userDataB.collisionCategory == CollisionCategory.AVATAR) {
			if (avatar.isDead()) {
				contact.setEnabled(false);
			}
		}
		
		if ((userDataA.collisionCategory == CollisionCategory.AVATAR && userDataB.collisionCategory == CollisionCategory.TRAVERSABLE_PLATFORM)
				|| (userDataB.collisionCategory == CollisionCategory.AVATAR && userDataA.collisionCategory == CollisionCategory.TRAVERSABLE_PLATFORM)) {

			if (avatar.wasDraggingDown || avatar.isClimbing()) {
				contact.setEnabled(false);
			}
		}
		else if (userDataA.collisionCategory == CollisionCategory.ENEMY && userDataB.collisionCategory == CollisionCategory.TRAVERSABLE_PLATFORM) {
			GameObject gameObject = objectManager.get(userDataA.id);			
			if (gameObject instanceof Climber) { 
				if (((Climber) gameObject).isClimbing()) {
					contact.setEnabled(false);
				}
			}
		}
		else if ((userDataB.collisionCategory == CollisionCategory.ENEMY && userDataA.collisionCategory == CollisionCategory.TRAVERSABLE_PLATFORM)) {
			GameObject gameObject = objectManager.get(userDataB.id);
			if (gameObject instanceof Climber) {
				if (((Climber) gameObject).isClimbing()) {
					contact.setEnabled(false);
				}
			}
		}		
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		Body bodyA = fixtureA.getBody();
		Body bodyB = fixtureB.getBody();
		GameObjectData userDataA = (GameObjectData) bodyA.getUserData();
		GameObjectData userDataB = (GameObjectData) bodyB.getUserData();
		if ((userDataA.collisionCategory == CollisionCategory.AVATAR && userDataB.collisionCategory == CollisionCategory.ENEMY)) {
			Avatar avatar = objectManager.getAvatar();
			if (!avatar.isInvicible()) {
				Vector2 positionA = bodyA.getPosition();
				Vector2 positionB = bodyB.getPosition();
				Vector2 b2a = positionA.sub(positionB).nor();
				bodyA.applyLinearImpulse(b2a.scl(200f), new Vector2(32, 32), true);					
				avatar.onHit();
			}
		}
		else if ((userDataB.collisionCategory == CollisionCategory.AVATAR && userDataA.collisionCategory == CollisionCategory.ENEMY)) {
			Avatar avatar = objectManager.getAvatar();
			if (!avatar.isInvicible()) {
				Vector2 positionA = bodyA.getPosition();
				Vector2 positionB = bodyB.getPosition();
				Vector2 b2a = positionA.sub(positionB).nor();
				bodyB.applyLinearImpulse(b2a.rotate(180f).scl(200f), new Vector2(32, 32), true);			
				avatar.onHit();
			}			
		}
		else if ((userDataB.collisionCategory == CollisionCategory.AVATAR && userDataA.collisionCategory == CollisionCategory.COLLECTABLE)) {
			Art.coinSound.play();
			if (!userDataA.hidden) {
				Avatar avatar = objectManager.getAvatar();
				avatar.onItemCollected((Collectable)objectManager.get(userDataA.id));
			}
			userDataA.hidden = true;
		}
		else if ((userDataA.collisionCategory == CollisionCategory.AVATAR && userDataB.collisionCategory == CollisionCategory.COLLECTABLE)) {
			Art.coinSound.play();
			if (!userDataB.hidden) {
				Avatar avatar = objectManager.getAvatar();
				avatar.onItemCollected((Collectable)objectManager.get(userDataB.id));
			}
			userDataB.hidden = true;
		}	
		else if (userDataA.collisionCategory == CollisionCategory.SOLID_PLATFORM && userDataB.collisionCategory != CollisionCategory.SOLID_PLATFORM) {
			userDataB.flying = false;
		}
		else if (userDataB.collisionCategory == CollisionCategory.SOLID_PLATFORM && userDataA.collisionCategory != CollisionCategory.SOLID_PLATFORM) {
			userDataA.flying = false;
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		if (fixtureA == null || fixtureB == null) {
			return;
		}
		
		Body bodyA = fixtureA.getBody();
		Body bodyB = fixtureB.getBody();
		GameObjectData userDataA = (GameObjectData) bodyA.getUserData();
		GameObjectData userDataB = (GameObjectData) bodyB.getUserData();
		
		if (userDataA.collisionCategory == CollisionCategory.SOLID_PLATFORM && userDataB.collisionCategory != CollisionCategory.SOLID_PLATFORM) {
			userDataB.flying = true;
		}
		else if (userDataB.collisionCategory == CollisionCategory.SOLID_PLATFORM && userDataA.collisionCategory != CollisionCategory.SOLID_PLATFORM) {
			userDataA.flying = true;
		}
	}



	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}



}
