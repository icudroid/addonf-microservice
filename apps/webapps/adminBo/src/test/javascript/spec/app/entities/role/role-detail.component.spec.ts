/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdminBoTestModule } from '../../../test.module';
import { RoleDetailComponent } from '../../../../../../main/webapp/app/entities/role/role-detail.component';
import { RoleService } from '../../../../../../main/webapp/app/entities/role/role.service';
import { Role } from '../../../../../../main/webapp/app/entities/role/role.model';

describe('Component Tests', () => {

    describe('Role Management Detail Component', () => {
        let comp: RoleDetailComponent;
        let fixture: ComponentFixture<RoleDetailComponent>;
        let service: RoleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [RoleDetailComponent],
                providers: [
                    RoleService
                ]
            })
            .overrideTemplate(RoleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RoleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Role(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.role).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
