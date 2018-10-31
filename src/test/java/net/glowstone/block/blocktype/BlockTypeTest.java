package net.glowstone.block.blocktype;

import static org.bukkit.Material.REDSTONE_TORCH_ON;
import static org.bukkit.Material.REDSTONE_TORCH_OFF;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;
import static org.mockito.Matchers.any;

import org.bukkit.Material;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import net.glowstone.EventFactory;
import net.glowstone.block.GlowBlock;

public class BlockTypeTest {

    @Mock
    private EventFactory eventFactory;

    private BlockType blockType;

    @Before
    public void setup() {
        EventFactory.setInstance(eventFactory);
    }

    @After
    public void tearDown() {
        reset(eventFactory);
    }

    @Test
    public void onRedstoneUpdate_WithRedstoneTypeBlock_SendsRedstoneEvent() {
        blockType = new BlockType();

        Set<Material> redstoneMaterials = new HashSet<Material>(Arrays.asList(
            REDSTONE_TORCH_ON,
            REDSTONE_TORCH_OFF
        ));

        for(Material material : redstoneMaterials) {
            GlowBlock mockBlock = Mockito.mock(GlowBlock.class);
            when(mockBlock.getType()).thenReturn(material);
            when(mockBlock.getTypeId()).thenReturn(material.getId());
            blockType.onRedstoneUpdate(mockBlock);
            verify(eventFactory).callEvent(any(BlockRedstoneEvent.class));
            reset(eventFactory);
        }
    }

    @Test
    public void onRedstoneUpdate_WithNonRedstoneTypeBlock_NoEventSent() {

    }
}
