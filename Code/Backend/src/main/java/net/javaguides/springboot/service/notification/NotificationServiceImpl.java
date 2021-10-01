package net.javaguides.springboot.service.notification;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Notification;
import net.javaguides.springboot.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	public NotificationServiceImpl(NotificationRepository notificationRepository) {
		super();
		this.notificationRepository = notificationRepository;
	}

	
	/** 
	 * @return List<Notification>
	 */
	@Override
	public List<Notification> getAllNotifications() {

		return notificationRepository.findAll();
	}

	
	/** 
	 * @param notification
	 */
	@Override
	public void saveNotification(Notification notification) {
		this.notificationRepository.save(notification);

	}

	
	/** 
	 * @param idal
	 * @return Notification
	 */
	@Override
	public Notification getNotificationByIdal(int idal) {

		return notificationRepository.getOne(idal);
	}

	
	/** 
	 * @param idal
	 */
	@Override
	public void deleteNotificationByIdal(int idal) {
		this.notificationRepository.deleteById(idal);

	}

}
