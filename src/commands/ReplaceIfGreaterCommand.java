package commands;

import core.Coordinates;
import core.Difficulty;
import core.LabWork;
import core.Person;
import exceptions.EmptyCollectionException;
import exceptions.LabWorkSearchException;
import exceptions.WrongElementsCountException;
import util.CollectionManager;
import util.Console;
import util.LabWorkAsker;

import java.time.ZonedDateTime;

/**
 * Command which replaces the element if he is greater than past.
 */
public class ReplaceIfGreaterCommand extends AbstractCommand{
    /**
     * Each command should be determined only once.
     */
    private CollectionManager cmanager;
    private LabWorkAsker asker;
    public ReplaceIfGreaterCommand(CollectionManager c, LabWorkAsker a) {
        super("replace_if_greater null {element}", "заменить значение по ключу, если новое значение больше старого");
        cmanager = c;
        asker = a;
    }

    /**
     * Executes the command.
     * @param param
     * @return error code, 0 - ok, 1 - standard error (byte)
     */
    @Override
    public byte exec(String param) {
        try {
            if (param.isEmpty()) {
                throw new WrongElementsCountException();
            }
            if (cmanager.getSize() == 0) {
                throw new EmptyCollectionException();
            }
            Integer id = Integer.parseInt(param);
            LabWork l = cmanager.getById(id);
            if (l == null) {
                throw new LabWorkSearchException();
            }
            String name = l.getName();
            Coordinates coordinates = l.getCoordinates();
            ZonedDateTime creationDate = l.getCreationDate();
            double minimalPoint = l.getMinimalPoint();
            long personalMaximum = l.getPersonalQualitiesMaximum();
            Difficulty difficulty = l.getDifficulty();
            Person author = l.getAuthor();

            if (asker.askQuestion("Хотите изменить имя?")) {
                name = asker.askName();
            }
            if (asker.askQuestion("Хотите изменить координаты?")) {
                coordinates = asker.askCoordinates();
            }
            if (asker.askQuestion("Хотите изменить минимальную оценку?")) {
                minimalPoint = asker.askMinimalPoint();
            }
            if (asker.askQuestion("Хотите изменить персональный максимум?")) {
                personalMaximum = asker.askPersonalQualitiesMaximum();
            }
            if (asker.askQuestion("Хотите изменить сложность?")) {
                difficulty = asker.askDifficulty();
            }
            if (asker.askQuestion("Хотите изменить автора?")) {
                author = asker.askAuthor();
            }
            LabWork newWork = new LabWork(id, name, coordinates, creationDate, minimalPoint, personalMaximum, difficulty, author);
            if (newWork.compareTo(l) > 0) {
                cmanager.removeFromCollectionByKey(id);
                cmanager.addToCollection(newWork, id);
                Console.println("LabWork изменен.");
            }
            else {
                Console.println("LabWork не изменен.");
            }
            return 0;
        } catch (LabWorkSearchException e) {
            Console.printerr("LabWork с таким id не найден.");
        } catch (EmptyCollectionException e) {
            Console.printerr("Коллекция пуста.");
        } catch (WrongElementsCountException e) {
            Console.printerr("Нужно использовать команду так: " + getName());
        } catch (NumberFormatException e){
            Console.printerr("Было введено не то число!");
        }
        return 1;
    }
}
