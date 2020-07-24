import random
from locust import HttpUser, task, between, TaskSet
import json

TESTING_DATA = [
    ("1", "1"),
    ("1,2", "3"),
    ("110,119", "229"),
    ("110,220,330,1,2,3,4,5,6,7,8,9,10", "715"),
    ("110\n220\n330,1,2,3,4,5,6,7,8,9\n10", "715"),
    ("//[*@]\n3*@4*@2*@1", "10"),
    ("//[$][@][)]\n4)3)2@1", "10"),
    ("//[$$$$$$$$$][@][)]\n4)3)2@1", "10"),
]


class UserBehaviour(TaskSet):
    # def on_start(self):

    @task
    def view_item(self):
        input_str, expected_result = random.choice(TESTING_DATA)
        response = self.client.post("/string/sum", json={"providedString": input_str})
        assert response.ok, \
            "Response status should be ok."
        assert expected_result == response.text, \
            f"Result should be match {expected_result}"


class User(HttpUser):
    tasks = [UserBehaviour]
    wait_time = between(5, 9)
