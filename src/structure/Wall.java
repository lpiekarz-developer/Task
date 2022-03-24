package structure;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

interface Structure {
    // zwraca dowolny element o podanym kolorze
    Optional<Block> findBlockByColor(String color);

    // zwraca wszystkie elementy z danego materiału
    List<Block> findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();
}

public class Wall implements Structure {
    private List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream()
                .filter(block -> block.getColor().equals(color))
                .findFirst();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return blocks.stream()
                .filter(block -> block.getMaterial().equals(material))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return blocks.stream()
                .mapToInt(this::countBlocks)
                .sum();
    }

    private int countBlocks(Block block) {
        return block instanceof CompositeBlock ?
               ((CompositeBlock) block).getBlocks().stream().mapToInt(this::countBlocks).sum() : 1;
    }
}

interface Block {
    String getColor();
    String getMaterial();
}

interface CompositeBlock extends Block {
    List<Block> getBlocks();
}