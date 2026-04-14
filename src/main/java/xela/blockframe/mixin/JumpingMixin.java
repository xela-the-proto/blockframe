package xela.blockframe.mixin;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.waypoints.WaypointTransmitter;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xela.blockframe.BlockFrame;

@Mixin(LivingEntity.class)
public abstract class JumpingMixin extends Entity implements Attackable, WaypointTransmitter {
	public JumpingMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Unique
	private @Nullable Player asPlayer() {
		return (Object) this instanceof Player p ? p : null;
	}

	@Inject(at =  @At("HEAD"), method = "tick")
	private void init(CallbackInfo ci) {

		BlockFrame.LOGGER.info("Jumping mixin");
		var player = asPlayer();
		if (player != null){

			while (player.isJumping()){
				BlockFrame.LOGGER.info("Jumping");
				//player.push(new Vec3(0,1,0));
			}

		}
	}
}
