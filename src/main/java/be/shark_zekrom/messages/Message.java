package be.shark_zekrom.messages;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public enum Message {

    PREFIX("BalloonPrefix",false),
    CONFIG_RELOAD("BalloonReload",true),
    BALLOON_REMOVED("BalloonsRemoved",true),
    BALLOON_SUMMONED("BalloonsSummoned",true),
    BALLOON_MENU_NAME("BalloonsMenuName",false),
    BALLOON_MENU_NEXT("BalloonsMenuNext",false),
    BALLOON_MENU_PREVIOUS("BalloonsMenuPrevious",false),
    BALLOON_MENU_REMOVE("BalloonsMenuRemove",false),
    BALLOON_NO_PERM("BalloonsMenuNoPermissionToSummon",true),
    BALLOONS_NO_CMD_PERM("BalloonsNoCommandPermission",true);

    private final String messageKey;
    private final boolean addPrefix;
    private final MessageService messageService;

    Message(String messageKey, boolean addPrefix) {
        this.messageKey = messageKey;
        this.addPrefix = addPrefix;
        this.messageService = MessageService.getInstance();
    }

    public void send(CommandSender sender) {
        sender.sendMessage(buildComponent());
    }

    public Component buildComponent() {
        if (this.addPrefix)
            return Component.text(messageService.localizeString(PREFIX.messageKey)
                    + messageService.localizeString(this.messageKey));
        else
            return Component.text(messageService.localizeString(this.messageKey));
    }

    public String buildString() {
        if (this.addPrefix)
            return messageService.localizeString(PREFIX.messageKey)
                    + messageService.localizeString(this.messageKey);
        else
            return messageService.localizeString(this.messageKey);
    }

}
