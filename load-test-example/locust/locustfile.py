from locust import HttpUser, task

class QuickTestUser(HttpUser):
    @task
    def ping(self):
        self.client.get("/", name="ping")