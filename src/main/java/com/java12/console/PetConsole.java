package com.java12.console;

import com.java12.App;
import com.java12.ConsoleInterface;
import com.java12.ConstUrls;
import com.java12.commands.PetCommands;
import com.java12.entity.pet.Category;
import com.java12.entity.pet.Pet;
import com.java12.entity.pet.Status;
import com.java12.entity.pet.Tag;
import com.java12.service.PetService;

import java.io.File;
import java.io.IOException;

public class PetConsole implements ConsoleInterface {
    private CommandPetUtils commandPetUtils = new CommandPetUtils();
    private PetService petService = new PetService();

    @Override
    public void execute() {
        System.out.println("\tHello, here you can do actions with pets");

        PetCommands petCommand;

        while (true) {
            System.out.println("\n");
            for (PetCommands commands : PetCommands.values()) {
                if (!commands.equals(PetCommands.UNKNOWN))
                    System.out.println(commands.getDescription());
            }
            System.out.print("\nEnter command: ");

            petCommand = commandPetUtils.getPetCommand(scanner.nextLine());

            switch (petCommand) {
                case GET_BY_ID:
                    getPetById();
                    break;

                case ADD:
                    postPetToStore();
                    break;

                case PUT:
                    putPetToStore();
                    break;

                case GET_BY_STATUS:
                    getPetsByStatus();
                    break;

                case UPDATE:
                    updatePetByIdStatusName();
                    break;

                case DELETE:
                    deleteById();
                    break;

                case UPLOAD_IMAGE:
                    uploadImage();
                    break;

                case EXIT:
                    App.menuWork();

                case UNKNOWN:
                    System.out.println("No such command, try again\n");
                    break;
            }
        }
    }

    public void getPetById() {
        System.out.print("Enter id of pet you want to get: ");

        int petId = Integer.parseInt(scanner.nextLine());
        String answer;
        try {
            answer = petService.getPetById(petId);
            if (answer.startsWith(ConstUrls.errorType)) System.out.println("\n" + answer);
            else {
                Pet pet = gson.fromJson(answer, Pet.class);
                System.out.println("\n" + pet.toString());
            }
        } catch (IOException e) {
            return;
        }
    }

    public void postPetToStore() {
        putAndPostPet(true);
    }

    // the same as post
    public void putPetToStore() {
        putAndPostPet(false);
    }

    public void getPetsByStatus() {
        int index = 0;
        System.out.println("Choose pet status: 1 - available, 2 - pending, 3 - sold");
        index = Integer.parseInt(scanner.nextLine());

        try {
            Pet[] pet = gson.fromJson(petService.getPetsByStatus(Status.values()[index - 1]), Pet[].class);

            for (Pet pet1 : pet) {
                System.out.println(pet1.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePetByIdStatusName() {
        System.out.println("Enter data of your pet: ");
        Pet pet = Pet.builder().build();
        System.out.println("Enter name of your pet: ");
        pet.setName(scanner.nextLine());
        System.out.println("Enter id of your pet: ");
        pet.setId(Integer.parseInt(scanner.nextLine()));
        System.out.println("Enter status of your pet: ");
        String line = scanner.nextLine();
        Status status = null;

        if (line.equalsIgnoreCase("available")) {
            status = Status.available;
        } else {
            if (line.equalsIgnoreCase("pending")) {
                status = Status.pending;
            } else {
                status = Status.sold;
            }
        }
        pet.setStatus(status);
        try {
            System.out.println(petService.updatePetByIdStatusName(pet));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteById() {
        System.out.println("Enter your pet id: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Are you sure?\n Enter (+) - to confirm: ");
        if (scanner.nextLine().contains("+")) {
            try {
                System.out.println(petService.deleteById(id));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Operation canceled");
        }
    }

    public void uploadImage() {
        System.out.println("Enter id of your pet: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter path of your file: ");
        File file = new File(scanner.nextLine());
        System.out.println("Enter format of your file: ");
        String format = scanner.nextLine();
        try {
            System.out.println(petService.uploadImage(id, file, format));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // extra service methods
    private Status getStatus(String name) {
        if (name.equalsIgnoreCase("available")) {
            return Status.available;
        } else {
            if (name.equalsIgnoreCase("pending")) {
                return Status.pending;
            } else {
                return Status.sold;
            }
        }
    }

    private void putAndPostPet(boolean flag) {
        Pet pet = Pet.builder().build();
        System.out.println("Enter information about new pet: ");
        System.out.println("####");
        System.out.println("Enter name: ");
        pet.setName(scanner.nextLine());
        System.out.println("Enter id: ");
        pet.setId(Integer.parseInt(scanner.nextLine()));
        System.out.println("Enter status: ");
        pet.setStatus(getStatus(scanner.nextLine()));

        Category category = Category.builder().build();
        System.out.println("Enter info about category of your pet: ");
        System.out.println("####");
        System.out.println("Enter id of category: ");
        category.setId(Integer.parseInt(scanner.nextLine()));
        System.out.println("Enter name of category: ");
        category.setName(scanner.nextLine());
        System.out.println("####");
        pet.setCategory(category);

        System.out.println("Let`s start with tags");
        System.out.println("####");
        System.out.println("Enter number of your tags: ");
        int tagNum = Integer.parseInt(scanner.nextLine());
        Tag[] tags = new Tag[tagNum];
        Tag current;
        for (int i = 0; i < tagNum; i++) {
            System.out.println("Enter data of your tag: ");
            current = Tag.builder().build();
            System.out.println("Enter name: ");
            current.setName(scanner.nextLine());
            System.out.println("Enter id: ");
            current.setId(Integer.parseInt(scanner.nextLine()));
            tags[i] = current;
        }

        pet.setTags(tags);
        System.out.println("####");

        System.out.println("Let`s start with photoUrls");
        System.out.println("####");
        System.out.println("Enter your photoUrls in line (example:\n firstUrl secondUrl thirdUrl): ");
        String[] urls = scanner.nextLine().split(" ");
        pet.setPhotoUrls(urls);
        System.out.println("####");

        try {
            if (flag)
                System.out.println(petService.postPetToStore(pet));
            else
                System.out.println(petService.putPetToStore(pet));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
