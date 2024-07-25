package farlgerann.immersivepunching.entity.Navi;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class NaviEntity extends AllayEntity {

    World world;

    public NaviEntity(EntityType<? extends AllayEntity> entityType, World world) {
        super(entityType, world);
        this.world = world;
    }

    public void onInitialize() {
        AttackEntityCallback.EVENT.register(this::onPlayerAttackEntity);
    }

    private ActionResult onPlayerAttackEntity(PlayerEntity player, net.minecraft.world.World world,
            net.minecraft.util.Hand hand, Entity entity,
            /* nullable */ EntityHitResult source) {
        // Logique personnalisée lorsque le joueur frappe une entité
        if (!world.isClient) {
            player.sendMessage(Text.literal("Vous avez frappé une entité !"), false);

            // Exemple : Donner un effet de potion au joueur
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 1));

            // Exemple : Vérifier le type d'entité et exécuter une logique spécifique
            if (entity instanceof CowEntity) {
                player.sendMessage(Text.literal("Vous avez frappé une vache !"), false);
                // Ajoute ici ta logique personnalisée pour une vache
            }
        }
        return ActionResult.SUCCESS;
    }
}
