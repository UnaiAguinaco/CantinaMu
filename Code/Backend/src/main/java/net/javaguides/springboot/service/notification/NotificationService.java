package net.javaguides.springboot.service.notification;

import java.util.List;

import net.javaguides.springboot.model.Notification;

public interface NotificationService {
	List<Notification> getAllNotifications();

	void saveNotification(Notification notification);

	Notification getNotificationByIdal(int idal);

	void deleteNotificationByIdal(int idal);

}
