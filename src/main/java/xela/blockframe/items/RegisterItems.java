package xela.blockframe.items;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import xela.blockframe.BlockFrame;

import java.util.function.Function;

public class RegisterItems {


    public static final Item LOTUS_FLOWER = registerItem("lotus_flower", Item::new, new Item.Properties());

    public static  void init(){
        /*
        java inits all the fields marked as static in a class, so we dont need to fill the init method and just add items
        by calling the register as static variables.
        We will be using it for tab assignation
         */

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register((creativeTab) -> creativeTab.accept(RegisterItems.LOTUS_FLOWER));
    };


                //Extend generic methods from T (parent class) to Item (child class)
                                                                //Function that will get Item.Properties and will output a generic T
    public static <T extends Item> T registerItem(String name, Function<Item.Properties,T> itemFactory, Item.Properties settings){
        //Create item key for minecarft registry.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM,
                // Generate an id from the mod and the name passed
                Identifier.fromNamespaceAndPath(BlockFrame.MOD_ID,name));
        //Create the item generic, we pass the Item.Properties and get a generic T in return
        T item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM,itemKey,item);

        return item;
    }
}
